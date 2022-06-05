package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

	private static final Logger logger = LogManager.getLogger(CurvePointTests.class.getName());

	@Autowired
	private CurvePointRepository repo;

	@Autowired
    @Qualifier("curvePointService")
    protected Services service;

	@Test
	public void curvePointTest() throws Exception {
		CurvePoint curvePoint = new CurvePoint(1, 10.0, 10.0);
		CurvePoint updatedCurvePoint = new CurvePoint(1, 20.0, 20.0);
		Integer id = 1;

		// Save
		List<CurvePoint> list = repo.findByCurveIdAndValue(curvePoint.getCurveId(), curvePoint.getValue());
		assertTrue(list.size() == 0);
		logger.info(curvePoint.toString());
		service.post(curvePoint);
		list = repo.findByCurveIdAndValue(curvePoint.getCurveId(), curvePoint.getValue());
		assertTrue(list.size() == 1);
		logger.info("CurvePoint successfuly saved !");
		id = list.get(0).getId();
		curvePoint.setId(list.get(0).getId());
		updatedCurvePoint.setId(list.get(0).getId());

		// Update
		service.put(updatedCurvePoint);
		assertEquals(updatedCurvePoint.toString(), repo.findById(id).get().toString());

		// Find
		assertNotNull(service.get(id));

		//Find All
		assertFalse(service.castList(CurvePoint.class, service.getAll()).isEmpty());

		// Delete
		service.delete(id);
		Optional<CurvePoint> optional = repo.findById(id);
		assertFalse(optional.isPresent());
	}

}
