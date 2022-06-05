package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

    List<BidList> findByAccountAndType(String account, String type);

}
