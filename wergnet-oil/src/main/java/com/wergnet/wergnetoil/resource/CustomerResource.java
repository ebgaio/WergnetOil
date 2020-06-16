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
import com.wergnet.wergnetoil.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerResource {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CustomerService customerService;
		
	@GetMapping
	public List<Customer> listAll() {
		return customerRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer, HttpServletResponse response) {
		Customer customerSave =  customerRepository.save(customer);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, customerSave.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(customerSave);
	}
	
	@PostMapping("/{codeCard}")
	public ResponseEntity<Card> createAddCard(@Valid @RequestBody Customer customer, @PathVariable Long codeCard, HttpServletResponse response) {
		
		Customer customerSave = customerRepository.save(customer);
		
		Optional<Card> cardSave = cardRepository.findById(codeCard);
		cardSave.get().setCustomer(customer);
		cardRepository.save(cardSave.get());
		
		publisher.publishEvent(new ResourceCreatedEvent(this, response, customerSave.getId()));
	
		return ResponseEntity.status(HttpStatus.CREATED).body(cardSave.get());
	}

	@GetMapping("/{code}")
	public ResponseEntity<Customer> getByCode(@PathVariable Long code) {
		Optional<Customer> customer = this.customerRepository.findById(code);
//		List<Card> card = this.cardRepository.getCardsByCustomer(code);
//		customer.get().setCards(card);
		return customer.isPresent() ? ResponseEntity.ok(customer.get()) : ResponseEntity.notFound().build();
//		return ResponseEntity.of(customer);
	}
	
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long code) {
		customerRepository.deleteById(code);
	}
	
	@PutMapping("/{code}")
	public ResponseEntity<Customer> update(@PathVariable Long code, @Valid @RequestBody Customer customer) {
		Customer customerSave = customerService.update(code, customer);
		return ResponseEntity.ok(customerSave);
	}
	
	@PutMapping("/{code}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyActive(@PathVariable Long code, @RequestBody Boolean active) {
		customerService.updatePropertyActive(code, active);
	}
	
}
