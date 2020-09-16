package com.wergnet.wergnetoil.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wergnet.wergnetoil.api.model.Card;
import com.wergnet.wergnetoil.api.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	public void updatePropertyActive(Long code, Boolean active) {
		Card cardSave = getCardByCode(code);
		cardSave.setActive(active);
		cardRepository.save(cardSave);
	}

	public Card getCardByCode(Long code) {
		Card cardSave = this.cardRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return cardSave;
	}
}
