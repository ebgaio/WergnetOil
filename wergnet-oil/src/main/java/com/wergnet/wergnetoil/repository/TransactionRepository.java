package com.wergnet.wergnetoil.repository;

import com.wergnet.wergnetoil.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
}
