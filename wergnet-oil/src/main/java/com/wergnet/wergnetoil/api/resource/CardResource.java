package com.wergnet.wergnetoil.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wergnet.wergnetoil.api.event.ResourceCreatedEvent;
import com.wergnet.wergnetoil.api.model.Card;
import com.wergnet.wergnetoil.api.model.Customer;
import com.wergnet.wergnetoil.api.repository.CardRepository;
import com.wergnet.wergnetoil.api.repository.CustomerRepository;
import com.wergnet.wergnetoil.api.service.CardNumberGenerator;
import com.wergnet.wergnetoil.api.service.CardService;

@RestController
@RequestMapping("/cards")
public class CardResource {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CardNumberGenerator randomCreditCardNumberGenerator;
	
	// List all Cards
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_CARD') and #oauth2.hasScope('read')")
	public List<Card> listAll() {
		return cardRepository.findAll();
	}
	
	// Create new card to a specific customerId
	@PostMapping("/{customerId}")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CARD') and #oauth2.hasScope('write')")
	public ResponseEntity<Card> createCard(@Valid @RequestBody Card card, @PathVariable Long customerId, HttpServletResponse response) {
		Customer customerSave = this.customerRepository.findById(customerId).orElseThrow(() -> new EmptyResultDataAccessException(1));
		String cardNumber = randomCreditCardNumberGenerator.generateNumber();
		card.setCardNumber(cardNumber);
		card.setCustomer(customerSave);
		Card cardSave = cardRepository.save(card);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, card.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cardSave);
	}

	// Show card by a code
	@GetMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_SEARCH_CARD') and #oauth2.hasScope('read')")
	public ResponseEntity<Card> getByCode(@PathVariable Long code, HttpServletResponse response) {
		Card card = this.cardRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
		publisher.publishEvent(new ResourceCreatedEvent(this, response, card.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(card);
	}
	
	// Delete a card by code 
	@DeleteMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_CARD') and #oauth2.hasScope('delete')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long code) {
		cardRepository.deleteById(code);
	}
	
	// Update card to specific code - Active true/false
	@PutMapping("/{code}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CARD') and #oauth2.hasScope('update')")
	public void updatePropertyActive(@PathVariable Long code, @RequestBody Boolean active) {
		cardService.updatePropertyActive(code, active);
	}
	
}