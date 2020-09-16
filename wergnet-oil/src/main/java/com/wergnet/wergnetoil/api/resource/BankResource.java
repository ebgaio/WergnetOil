package com.wergnet.wergnetoil.api.resource;

import com.wergnet.wergnetoil.api.model.Bank;
import com.wergnet.wergnetoil.api.repository.BankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankResource {

    @Autowired
    private BankRepository bankRepository;

    @GetMapping
	@PreAuthorize("hasAuthority('ROLE_SEARCH_BANK') and #oauth2.hasScope('read')")
    public List<Bank> listAll() {
        return bankRepository.findAll();
    }
}
