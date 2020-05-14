package com.wergnet.wergnetoil.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wergnet.wergnetoil.event.ResourceCreatedEvent;
import com.wergnet.wergnetoil.model.Customer;
import com.wergnet.wergnetoil.repopsitory.CustomerRepository;

@RestController
@RequestMapping("/customers")
public class CustomerResource {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
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

	@GetMapping("/{code}")
	public ResponseEntity<Customer> getByCode(@PathVariable Long code) {
		Optional<Customer> customer = this.customerRepository.findById(code);
		return customer.isPresent() ? ResponseEntity.ok(customer.get()) : ResponseEntity.notFound().build();
	}
}
