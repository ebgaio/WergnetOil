package com.wergnet.wergnetoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wergnet.wergnetoil.model.Card;
import com.wergnet.wergnetoil.repository.card.CardRepositoryQuery;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryQuery {
	
//	@Query("SELECT c FROM Card c WHERE c.customer = :code")
//	public Optional<Card> getCardsByCode(@Param("code") Long code);

}
