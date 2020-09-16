package com.wergnet.wergnetoil.api.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wergnet.wergnetoil.api.model.Bank;
import com.wergnet.wergnetoil.api.model.Customer;
import com.wergnet.wergnetoil.api.model.Transaction;
import com.wergnet.wergnetoil.api.repository.TransactionRepository;
import com.wergnet.wergnetoil.api.service.exception.BankNotFoundOrInactiveException;
import com.wergnet.wergnetoil.api.service.exception.CustomerNotFoundOrInactiveException;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CardService cardService;

	@Autowired
	private BankService bankService;
	
	public Transaction save(@Valid Transaction transaction) {
		Customer customerSaved =  this.customerService.getCustomerByCode(transaction.getCustomer().getId());
		Bank bankSaved = this.bankService.getBankByCode(transaction.getBank().getId());
		if (customerSaved == null || customerSaved.isInactive()) {
			throw new CustomerNotFoundOrInactiveException();
		}
		if (bankSaved == null || bankSaved.isInactive()) {
			throw new BankNotFoundOrInactiveException();
		}
		return transactionRepository.save(transaction);
	}

}
