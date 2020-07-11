package com.wergnet.wergnetoil.resource;

import com.wergnet.wergnetoil.model.Bank;
import com.wergnet.wergnetoil.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Bank> listAll() {
        return bankRepository.findAll();
    }
}
