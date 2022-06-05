package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidTests {

	private static final Logger logger = LogManager.getLogger(BidTests.class.getName());

	@Autowired
	private BidListRepository repo;

	@Autowired
    @Qualifier("bidService")
    protected Services service;

	@Test
	public void bidListTest() throws Exception { 
		BidList bid = new BidList("TestAccount", "TestType", 10.0);
		BidList updatedBid = new BidList("UpdatedAccount", "UpdatedType", 20.0);
		Integer id = 1;

		// Save
		List<BidList> list = repo.findByAccountAndType(bid.getAccount(), bid.getType());
		assertTrue(list.size() == 0);
		logger.info(bid.toString());
		service.post(bid);
		list = repo.findByAccountAndType(bid.getAccount(), bid.getType());
		assertTrue(list.size() == 1);
		logger.info("Bid successfuly saved !");
		id = list.get(0).getBidListId();
		bid.setBidListId(list.get(0).getBidListId());
		updatedBid.setBidListId(list.get(0).getBidListId());

		// Update
		service.put(updatedBid);
		assertEquals(updatedBid.toString(), repo.findById(id).get().toString());

		// Find
		assertNotNull((BidList) service.get(id));

		//Find All
		assertFalse(service.castList(BidList.class, service.getAll()).isEmpty());

		// Delete
		service.delete(id);
		Optional<BidList> optional = repo.findById(id);
		assertFalse(optional.isPresent());
	}
}
