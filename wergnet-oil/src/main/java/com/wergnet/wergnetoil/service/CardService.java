package com.wergnet.wergnetoil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wergnet.wergnetoil.model.Card;
import com.wergnet.wergnetoil.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
	CardRepository cardRepository;
	
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
