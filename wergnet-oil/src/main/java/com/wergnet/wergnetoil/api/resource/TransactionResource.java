package com.wergnet.wergnetoil.api.resource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wergnet.wergnetoil.api.event.ResourceCreatedEvent;
import com.wergnet.wergnetoil.api.exceptionHandler.WergnetOilExceptionHandler.Erro;
import com.wergnet.wergnetoil.api.model.Bank;
import com.wergnet.wergnetoil.api.model.Customer;
import com.wergnet.wergnetoil.api.model.Transaction;
import com.wergnet.wergnetoil.api.repository.BankRepository;
import com.wergnet.wergnetoil.api.repository.CustomerRepository;
import com.wergnet.wergnetoil.api.repository.TransactionRepository;
import com.wergnet.wergnetoil.api.repository.filter.TransactionFilter;
import com.wergnet.wergnetoil.api.repository.projection.TransactionSummary;
import com.wergnet.wergnetoil.api.service.TransactionService;
import com.wergnet.wergnetoil.api.service.exception.InsufficientFundsInCardException;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private MessageSource messageSource;
    
    // Show transactions unique/limited by date | localhost:8080/transactions
    @GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_TRANSACTION') and #oauth2.hasScope('read')")
    public Page<Transaction> research(TransactionFilter transactionFilter, Pageable pageable) {
    	
        return transactionRepository.filter(transactionFilter, pageable);
    }
    
    //Get a summarized values of transaction | localhost:8080/transactions?summary&dateDebitFrom=2017-02-20&dateDebitTo=2017-10-20&size=2&page=0
    @GetMapping(params = "summary")
	@PreAuthorize("hasAuthority('ROLE_SEARCH_TRANSACTION') and #oauth2.hasScope('read')")
    public Page<TransactionSummary> summarize(TransactionFilter transactionFilter, Pageable pageable) {
    	
        return transactionRepository.summarize(transactionFilter, pageable);
    }
    
    // Show transaction by code | localhost:8080/transactions/2
    @GetMapping("/{code}")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_TRANSACTION') and #oauth2.hasScope('read')")
    public ResponseEntity<Transaction> getByCode(@PathVariable Long code) {
    	
    	Optional<Transaction> transaction = this.transactionRepository.findById(code); 
    	return transaction.isPresent() ? ResponseEntity.ok(transaction.get()) : ResponseEntity.notFound().build();
    }
    
    // Create transaction | localhost:8080/transactions
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TRANSACTION') and #oauth2.hasScope('write')")
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction, HttpServletResponse response) {
    	
    	Transaction transactionSaved = transactionService.save(transaction);
    	publisher.publishEvent(new ResourceCreatedEvent(this, response, transactionSaved.getId()));
    	return ResponseEntity.status(HttpStatus.CREATED).body(transactionSaved);
    }
    
	/* Use of alternative option of @PostMapping : 
	 * @PostMapping(value = ("/{customer}"), params = {"bank", "card"} )
	 * public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction, @PathVariable Long customer, @RequestParam Long bank, @RequestParam Long card, HttpServletResponse response) { 
	 */
    // Create transaction by customer and a bank | localhost:8080/transactions?customer=1&bank=2
    @PostMapping(params = { "customer", "bank"})
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TRANSACTION') and #oauth2.hasScope('write')")
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction, @RequestParam Long customer, @RequestParam Long bank, HttpServletResponse response) {

    	Customer customerSave = this.customerRepository.findById(customer).orElseThrow(() -> new EmptyResultDataAccessException(1));
    	Bank bankSave = this.bankRepository.findById(bank).orElseThrow(() -> new EmptyResultDataAccessException(1));
    	
    	transaction.setCustomer(customerSave);
    	transaction.setBank(bankSave);
    	Transaction transactionSaved = transactionRepository.save(transaction);
    	
    	publisher.publishEvent(new ResourceCreatedEvent(this, response, transactionSaved.getId()));
    	return ResponseEntity.status(HttpStatus.CREATED).body(transactionSaved);
    }
    
    // Create transaction for to debit value in card of customer | localhost:8080/transactions?value=20&card=2
    @PostMapping(params = {"value", "card"})
    @PreAuthorize("hasAuthority('ROLE_REGISTER_TRANSACTION') and #oauth2.hasScope('write')")
    public ResponseEntity<Transaction> buyCreditToCard(@Valid @RequestBody Transaction transaction, @RequestParam BigDecimal value, @RequestParam Long card, HttpServletResponse response) {
    	
    	Transaction transactionSaved = transactionService.buyCreditToCard(transaction, value, card);
    	publisher.publishEvent(new ResourceCreatedEvent(this, response, transactionSaved.getId()));
    	return ResponseEntity.status(HttpStatus.CREATED).body(transactionSaved);
    }
    
    // Send message when has insufficient funds in Card.
    @ExceptionHandler({ InsufficientFundsInCardException.class })
    public ResponseEntity<Object> handlerInsufficientFundsInCardException(InsufficientFundsInCardException ex) {
		String mesageUser = messageSource.getMessage("card.insufficient-funds", null, LocaleContextHolder.getLocale());
		String mesageDeveloper = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mesageUser, mesageDeveloper));
		return ResponseEntity.badRequest().body(erros);
    }

//    @PostMapping(params = {"value", "card"}) //("/{code}/active")
//    @PreAuthorize("hasAuthority('ROLE_REGISTER_TRANSACTION') and #oauth2.hasScope('write')")
//    public ResponseEntity<Transaction> getMoneyFromCard() {
//    	
//    	
//    	return null;
////    	return ResponseEntity.status(HttpStatus.CREATED).body(transactionSaved);
//    }

}
