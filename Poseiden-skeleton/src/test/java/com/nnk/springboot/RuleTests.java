package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

	private static final Logger logger = LogManager.getLogger(RuleTests.class.getName());

	@Autowired
	private RuleNameRepository repo;

	@Autowired
    @Qualifier("ruleNameService")
    protected Services service;

	@Test
	public void ruleTest() throws NotFoundException {
		RuleName ruleName = new RuleName("TestName", "TestDescription");
		RuleName updatedRuleName = new RuleName("UpdatedName", "UpdatedDescription");
		Integer id;

		// Save
		List<RuleName> list = repo.findByNameAndDescription(ruleName.getName(), ruleName.getDescription());
		assertTrue(list.size() == 0);
		logger.info(ruleName.toString());
		service.post(ruleName);
		list = repo.findByNameAndDescription(ruleName.getName(), ruleName.getDescription());
		assertTrue(list.size() == 1);
		logger.info("RuleName successfuly saved !");
		id = list.get(0).getId();
		ruleName.setId(list.get(0).getId());
		updatedRuleName.setId(list.get(0).getId());

		// Update
		service.put(updatedRuleName);
		assertEquals(updatedRuleName.toString(), repo.findById(id).get().toString());

		// Find
		assertNotNull(service.get(id));

		//Find All
		assertFalse(service.castList(RuleName.class, service.getAll()).isEmpty());

		// Delete
		service.delete(id);
		Optional<RuleName> optional = repo.findById(id);
		assertFalse(optional.isPresent());
	}
}
