package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeTests {

	private static final Logger logger = LogManager.getLogger(TradeTests.class.getName());

	@Autowired
	private TradeRepository repo;

	@Autowired
    @Qualifier("tradeService")
    protected Services service;

	@Test
	public void tradeTest() throws NotFoundException {
		Trade trade = new Trade("TestAccount", "TestType", 10.0);
		Trade updatedTrade = new Trade("UpdatedAccount", "UpdatedType", 20.0);
		Integer id = 1;

		// Save
		List<Trade> list = repo.findByAccountAndType(trade.getAccount(), trade.getType());
		assertTrue(list.size() == 0);
		logger.info(trade.toString());
		service.post(trade);
		list = repo.findByAccountAndType(trade.getAccount(), trade.getType());
		assertTrue(list.size() == 1);
		logger.info("Trade successfuly saved !");
		id = list.get(0).getTradeId();
		trade.setTradeId(list.get(0).getTradeId());
		updatedTrade.setTradeId(list.get(0).getTradeId());

		// Update
		service.put(updatedTrade);
		assertEquals(updatedTrade.toString(), repo.findById(id).get().toString());

		// Find
		assertNotNull(service.get(id));

		//Find All
		assertFalse(service.castList(Trade.class, service.getAll()).isEmpty());

		// Delete
		service.delete(id);
		Optional<Trade> optional = repo.findById(id);
		assertFalse(optional.isPresent());
	}
}
