package com.wergnet.wergnetoil.api.repository.transaction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.wergnet.wergnetoil.api.model.Bank_;
import com.wergnet.wergnetoil.api.model.Customer_;
import com.wergnet.wergnetoil.api.model.Transaction;
import com.wergnet.wergnetoil.api.model.Transaction_;
import com.wergnet.wergnetoil.api.repository.filter.TransactionFilter;
import com.wergnet.wergnetoil.api.repository.projection.TransactionSummary;

public class TransactionRepositoryImpl implements TransactionRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Transaction> filter(TransactionFilter transactionFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
		Root<Transaction> root =  criteria.from(Transaction.class);

		// create the restrictions
		Predicate[] predicates = creatingRestrictions(transactionFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Transaction> query = manager.createQuery(criteria);
		addPaginationRestrictions(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(transactionFilter));
	}
	
	@Override
	public Page<TransactionSummary> summarize(TransactionFilter transactionFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TransactionSummary> criteria = builder.createQuery(TransactionSummary.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		
		criteria.select(builder.construct(TransactionSummary.class
				, root.get(Transaction_.id)
				, root.get(Transaction_.description)
				, root.get(Transaction_.balanceCard)
				, root.get(Transaction_.dateCredit)
				, root.get(Transaction_.dateDebit) 
				, root.get(Transaction_.customer).get(Customer_.nameCustomer)
				, root.get(Transaction_.bank).get(Bank_.name)));
		
		Predicate[] predicates = creatingRestrictions(transactionFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<TransactionSummary> query = manager.createQuery(criteria);
		addPaginationRestrictions(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(transactionFilter));
	}

	private Predicate[] creatingRestrictions(TransactionFilter transactionFilter, CriteriaBuilder builder,
			Root<Transaction> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		// where descricao like '%aadsdfsasd%'
		if (!StringUtils.isEmpty(transactionFilter.getDescription())) {
			predicates.add(builder.like(
					builder.lower(root.get(Transaction_.DESCRIPTION)), "%" + transactionFilter.getDescription().toLowerCase() + "%"));
		}
		
		if (transactionFilter.getDateCreditFrom() != null) {
		predicates.add(
				builder.greaterThanOrEqualTo(root.get(Transaction_.DATE_CREDIT), transactionFilter.getDateCreditFrom()));
		}
		
		if (transactionFilter.getDateCreditTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Transaction_.DATE_CREDIT), transactionFilter.getDateCreditTo()));
		}
		
		if (transactionFilter.getDateDebitFrom() != null) {
		predicates.add(
				builder.greaterThanOrEqualTo(root.get(Transaction_.DATE_DEBIT), transactionFilter.getDateDebitFrom()));
		}
		
		if (transactionFilter.getDateDebitTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Transaction_.DATE_DEBIT), transactionFilter.getDateDebitTo()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void addPaginationRestrictions(TypedQuery<?> query, Pageable pageable) {
		int actualPage = pageable.getPageNumber();
		int totalRegistrationPerPage = pageable.getPageSize();
		int firstRegistrationOfPage = actualPage * totalRegistrationPerPage;
		
		query.setFirstResult(firstRegistrationOfPage);
		query.setMaxResults(totalRegistrationPerPage);
	}
	
	private Long total(TransactionFilter transactionFilter) {
		CriteriaBuilder builder =  manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		
		Predicate[] predicates = creatingRestrictions(transactionFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
