package com.wergnet.wergnetoil.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.wergnet.wergnetoil.api.model.Bank;
import com.wergnet.wergnetoil.api.repository.BankRepository;

@Service
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
	public void updatePropertyActive(Long code, Boolean active) {
		Bank bankSave = getBankByCode(code);
		bankSave.setActive(active);
		bankRepository.save(bankSave);
	}

	public Bank getBankByCode(Long code) {
		Bank bankSave = this.bankRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
		return bankSave;
	}

}
