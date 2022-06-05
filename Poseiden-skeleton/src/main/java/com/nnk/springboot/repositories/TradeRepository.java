package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {

    List<Trade> findByAccountAndType(String account, String type);

}
