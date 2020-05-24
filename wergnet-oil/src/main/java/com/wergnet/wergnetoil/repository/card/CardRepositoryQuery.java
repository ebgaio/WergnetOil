package com.wergnet.wergnetoil.repository.card;

import java.util.List;

import com.wergnet.wergnetoil.model.Card;

public interface CardRepositoryQuery {
	
	public List<Card> getCardsByCustomer(Long code);

}
