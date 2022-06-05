package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    List<CurvePoint> findByCurveIdAndValue(Integer curveId, Double value);

}
