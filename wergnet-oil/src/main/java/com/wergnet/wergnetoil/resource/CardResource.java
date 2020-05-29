package com.wergnet.wergnetoil.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wergnet.wergnetoil.event.ResourceCreatedEvent;
import com.wergnet.wergnetoil.model.Card;
import com.wergnet.wergnetoil.model.Customer;
import com.wergnet.wergnetoil.repository.CardRepository;
import com.wergnet.wergnetoil.repository.CustomerRepository;
import com.wergnet.wergnetoil.service.CardNumberGenerator;
import com.wergnet.wergnetoil.service.CardService;


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
	
	@GetMapping
	private List<Card> listAll() {
		return cardRepository.findAll();
	}
	
	@PostMapping("/{customerId}")
	public ResponseEntity<Card> createCard(@Valid @RequestBody Card card, @PathVariable Long customerId, HttpServletResponse response) {
		Optional<Customer> customerSave = customerRepository.findById(customerId);
		String cardNumber = randomCreditCardNumberGenerator.generateNumber();
		card.setCardNumber(cardNumber);
		card.setCustomer(customerSave.get());
		Card cardSave = cardRepository.save(card);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, card.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cardSave);
	}

	@GetMapping("/{code}")
	private ResponseEntity<Card> getByCode(@PathVariable Long code) {
		Optional<Card> card = this.cardRepository.findById(code);
		System.out.println(card.get().getCustomer().getNameCustomer());
		return card.isPresent() ? ResponseEntity.ok(card.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long code) {
		cardRepository.deleteById(code);
	}
	
	@PutMapping("/{code}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyActive(@PathVariable Long code, @RequestBody Boolean active) {
		cardService.updatePropertyActive(code, active);
	}
	
}