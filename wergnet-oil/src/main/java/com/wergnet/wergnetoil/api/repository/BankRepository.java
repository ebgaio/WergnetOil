package com.wergnet.wergnetoil.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wergnet.wergnetoil.api.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
