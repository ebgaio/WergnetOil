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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wergnet.wergnetoil.event.ResourceCreatedEvent;
import com.wergnet.wergnetoil.model.Card;
import com.wergnet.wergnetoil.model.Customer;
import com.wergnet.wergnetoil.repopsitory.CardRepository;


@RestController
@RequestMapping("/cards")
public class CardResource {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	private List<Card> listAll() {
		return cardRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Card> createCard(@Valid @RequestBody Card card, HttpServletResponse response) {
//		card.setCustomer(new Customer(1L));
		Card cardSave = cardRepository.save(card);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, card.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cardSave);
	}

	@GetMapping("/{code}")
	private ResponseEntity<Card> getByCode(@PathVariable Long code) {
		Optional<Card> card = this.cardRepository.findById(code);
		return card.isPresent() ? ResponseEntity.ok(card.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long code) {
		cardRepository.deleteById(code);
	}
	
}