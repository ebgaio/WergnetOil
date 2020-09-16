package com.wergnet.wergnetoil.api.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wergnet.wergnetoil.api.model.Customer;
import com.wergnet.wergnetoil.api.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public void updatePropertyActive(Long code, Boolean active) {
		Customer customerSave = getCustomerByCode(code);
		customerSave.setActive(active);
		customerRepository.save(customerSave);
	}

	public Customer update(Long code, @Valid Customer customer) {
		Customer customerSave = getCustomerByCode(code);
		BeanUtils.copyProperties(customer, customerSave, "id", "cards");
		return customerRepository.save(customerSave);
	}
	
	public Customer getCustomerByCode(Long code) {
		Customer customerSave = this.customerRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return customerSave;
	}
	
}
