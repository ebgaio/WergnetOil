package com.wergnet.wergnetoil.repository.card;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.wergnet.wergnetoil.model.Card;

public class CardRepositoryImpl implements CardRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Card> getCardsByCustomer(Long code) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Card> query = builder.createQuery(Card.class);
		Root<Card> cardRoot = query.from(Card.class);
		
		ParameterExpression<Long> card = builder.parameter(Long.class);
		query.select(cardRoot).where(builder.equal(cardRoot.get("id"), card));
		
		TypedQuery<Card> typeQuery = manager.createQuery(query);
		typeQuery.setParameter(card, code);
		List<Card> results = typeQuery.getResultList();
		
//		query.where(builder.equal(cardRoot.get("customer"), card));
//		List<Card> resultList = manager.createQuery(query).getResultList();
		return results;
	}
	
//	@Override
//	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
//		
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
//		
//		Root<Lancamento> root = criteria.from(Lancamento.class);
//		
//		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
//		criteria.where(predicates);
//		
//		TypedQuery<Lancamento> query = manager.createQuery(criteria);
//		adiconarRestricoesDePaginacao(query, pageable);
//		
//		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
//	}
//
//	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
//			Root<Lancamento> root) {
//		List<Predicate> predicates = new ArrayList<>();
//		
//		// where descricao like '%aadsdfsasd%'
//		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
//			predicates.add(builder.like(
//					builder.lower(root.get(Lancamento_.DESCRICAO)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
//		}
//		
//		if (lancamentoFilter.getDataVencimentoDe() != null) {
//			predicates.add(
//					builder.greaterThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoDe()));
//		}
//		
//		if (lancamentoFilter.getDataVencimentoAte() != null) {
//			predicates.add(
//					builder.lessThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), lancamentoFilter.getDataVencimentoAte()));
//		}
//		return predicates.toArray(new Predicate[predicates.size()]);
//	}
//	
//	private void adiconarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
//		int paginaAtual = pageable.getPageNumber();
//		int totalRegistroPorPagina = pageable.getPageSize();
//		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
//		
//		query.setFirstResult(primeiroRegistroDaPagina);
//		query.setMaxResults(totalRegistroPorPagina);
//		
//	}
//	
//	private Long total(LancamentoFilter lancamentoFilter) {
//		CriteriaBuilder builder =  manager.getCriteriaBuilder();
//		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
//		Root<Lancamento> root = criteria.from(Lancamento.class);
//		
//		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
//		criteria.where(predicates);
//		
//		criteria.select(builder.count(root));
//		return manager.createQuery(criteria).getSingleResult();
//	}

//	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//	CriteriaQuery<Person> query = builder.createQuery(Person.class);
//	Root<Person> personRoot = query.from(Person.class);
//	query.where(builder.equal(personRoot.get("firstName"), "Homer"));
//	List<Person> resultList = entityManager.createQuery(query).getResultList();
	
}
