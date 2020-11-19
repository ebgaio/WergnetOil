package com.wergnet.wergnetoil.api.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wergnet.wergnetoil.api.model.Bank;
import com.wergnet.wergnetoil.api.model.Card;
import com.wergnet.wergnetoil.api.model.Customer;
import com.wergnet.wergnetoil.api.model.Transaction;
import com.wergnet.wergnetoil.api.repository.BankRepository;
import com.wergnet.wergnetoil.api.repository.CardRepository;
import com.wergnet.wergnetoil.api.repository.CustomerRepository;
import com.wergnet.wergnetoil.api.repository.TransactionRepository;
import com.wergnet.wergnetoil.api.service.exception.BankNotFoundOrInactiveException;
import com.wergnet.wergnetoil.api.service.exception.CustomerNotFoundOrInactiveException;
import com.wergnet.wergnetoil.api.service.exception.InsufficientFundsInCardException;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private CardRepository cardRepository;
	
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BankRepository bankRepository;
	
	@Transactional
	public Transaction save(@Valid Transaction transaction) {
		
		Customer customerSaved =  this.customerService.getCustomerByCode(transaction.getCustomer().getId());
		Bank bankSaved = this.bankService.getBankByCode(transaction.getBank().getId());
		
		if (customerSaved == null || customerSaved.isInactive()) {
			throw new CustomerNotFoundOrInactiveException();
		}
		if (bankSaved == null || bankSaved.isInactive()) {
			throw new BankNotFoundOrInactiveException();
		}
		transactionRepository.save(transaction);
		
		return transaction;
	}

	public Transaction createTransactionByCustomerByBank(Transaction transaction, Long customer, Long bank,
			HttpServletResponse response) {
		Customer customerSave = this.customerRepository.findById(customer).orElseThrow(() -> new EmptyResultDataAccessException(1));
    	Bank bankSave = this.bankRepository.findById(bank).orElseThrow(() -> new EmptyResultDataAccessException(1));
    	
    	transaction.setCustomer(customerSave);
    	transaction.setBank(bankSave);
    	Transaction transactionSaved = transactionRepository.save(transaction);
    	
		return transactionSaved;
	}
	
	@Transactional
	public Transaction buyCreditToCard(Transaction transaction, BigDecimal value, Long card) {

    	Card cardOfCustomer = this.cardRepository.findById(card).orElseThrow(() -> new EmptyResultDataAccessException(1));
    	
    	int r = cardOfCustomer.getBalance().compareTo(value);
    	if (r == 0 || r == 1) {
    		BigDecimal valueActual = cardOfCustomer.getBalance().subtract(value);
    		cardOfCustomer.setBalance(valueActual);  // The balance is equal or greater that value
		} else { 
			if (r == -1) {
				throw new InsufficientFundsInCardException();
			}
		}
    	cardRepository.save(cardOfCustomer);
    	transaction.setValueTransaction(value);
    	transaction = bankService.defaultBank(transaction);
    	transactionRepository.save(transaction);

    	return transaction;
	}

}
