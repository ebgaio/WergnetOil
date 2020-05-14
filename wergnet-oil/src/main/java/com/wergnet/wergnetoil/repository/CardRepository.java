package com.wergnet.wergnetoil.repopsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wergnet.wergnetoil.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

}
