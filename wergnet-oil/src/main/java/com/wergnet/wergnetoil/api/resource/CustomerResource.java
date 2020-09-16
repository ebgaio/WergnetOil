package com.wergnet.wergnetoil.api.resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.wergnet.wergnetoil.api.service.CustomerService;

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
	
	@Autowired
	private CardNumberGenerator randomCreditCardNumberGenerator;
	
	// List all Customers
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_CUSTOMER') and #oauth2.hasScope('read')")
	public List<Customer> listAll() {
		return customerRepository.findAll();
	}

	// Create a new Customer
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CUSTOMER') and #oauth2.hasScope('write')")
	public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer, HttpServletResponse response) {
		Customer customerSave =  customerRepository.save(customer);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, customerSave.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(customerSave);
	}
	
	// Create new Customer with a specific codeCard
//	@PostMapping(value = "/{codeCard}", params = "asdf")
	@PostMapping("/{codeCard}")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CUSTOMER') and #oauth2.hasScope('write')")
	public ResponseEntity<Card> createCustomerWithCard(@Valid @RequestBody Customer customer, @PathVariable Long codeCard, HttpServletResponse response) {
		Customer customerSave = customerRepository.save(customer);
		Optional<Card> cardSave = cardRepository.findById(codeCard);
		cardSave.get().setCustomer(customerSave);
		cardRepository.save(cardSave.get());
		publisher.publishEvent(new ResourceCreatedEvent(this, response, customerSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(cardSave.get());
	}
	
	// Create new Card to a specific codeCustomer
	@PostMapping("/card/{codeCustomer}")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CUSTOMER') and #oauth2.hasScope('write')")
	public ResponseEntity<Card> createNewCardToCustomer(@RequestBody Customer codeCustomer, HttpServletResponse response) {
		String cardNumber = randomCreditCardNumberGenerator.generateNumber();
		Optional<Customer> customerSaved = customerRepository.findById(codeCustomer.getId());
		Card card = new Card();
		card.setCardNumber(cardNumber);
		card.setCustomer(customerSaved.get());
		card.setActive(true);
		card.setBalance(new BigDecimal(0));
		cardRepository.save(card);
		return ResponseEntity.status(HttpStatus.CREATED).body(card);
	}

	// Show customer by code
	@GetMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_SEARCH_CUSTOMER') and #oauth2.hasScope('read')")
	public ResponseEntity<Customer> getByCode(@PathVariable Long code) {
		Optional<Customer> customer = this.customerRepository.findById(code);
//		List<Card> card = this.cardRepository.getCardsByCustomer(code);
//		customer.get().setCards(card);
		return customer.isPresent() ? ResponseEntity.ok(customer.get()) : ResponseEntity.notFound().build();
//		return ResponseEntity.of(customer);
	}

	// Delete customer by code
	@DeleteMapping("/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVE_CUSTOMER') and #oauth2.hasScope('delete')")
	public void delete(@PathVariable Long code) {
		customerRepository.deleteById(code);
	}
	
	// Update customer to a specific code
	@PutMapping("/{code}")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CUSTOMER') and #oauth2.hasScope('update')")
	public ResponseEntity<Customer> update(@PathVariable Long code, @Valid @RequestBody Customer customer) {
		Customer customerSave = customerService.update(code, customer);
		return ResponseEntity.ok(customerSave);
	}
	
	// Update customer to specific code - Active true/false
	@PutMapping("/{code}/active")
	@PreAuthorize("hasAuthority('ROLE_REGISTER_CUSTOMER') and #oauth2.hasScope('update')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyActive(@PathVariable Long code, @RequestBody Boolean active) {
		customerService.updatePropertyActive(code, active);
	}
	
}
