package com.wergnet.wergnetoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wergnet.wergnetoil.model.Customer;
import com.wergnet.wergnetoil.repository.card.CardRepositoryQuery;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
