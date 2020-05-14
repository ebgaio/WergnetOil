package com.wergnet.wergnetoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wergnet.wergnetoil.model.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

}
