package com.wergnet.wergnetoil.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wergnet.wergnetoil.model.Customer;
import com.wergnet.wergnetoil.repopsitory.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public void updatePropertyActive(Long code, Boolean active) {
		Customer customerSave = getCustomerByCode(code);
		customerSave.setActive(active);
		customerRepository.save(customerSave);
	}

	public Customer update(Long code, @Valid Customer customer) {
		Customer customerSave = getCustomerByCode(code);
		return customerRepository.save(customerSave);
	}
	
	public Customer getCustomerByCode(Long code) {
		Customer customerSave = this.customerRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return customerSave;
	}
	
}
