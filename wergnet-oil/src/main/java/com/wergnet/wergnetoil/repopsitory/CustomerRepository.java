package com.wergnet.wergnetoil.repopsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wergnet.wergnetoil.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
