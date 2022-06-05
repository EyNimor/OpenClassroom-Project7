package com.nnk.springboot.repositories;

import java.util.List;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

    List<RuleName> findByNameAndDescription(String name, String description);
}
