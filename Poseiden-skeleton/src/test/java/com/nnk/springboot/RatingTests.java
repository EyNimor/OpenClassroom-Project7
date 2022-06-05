package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
public class RatingTests {

	private static final Logger logger = LogManager.getLogger(RatingTests.class.getName());

	@Autowired
	private RatingRepository repo;

	@Autowired
    @Qualifier("ratingService")
    protected Services service;

	@Test
	public void ratingTest() throws Exception {
		Rating rating = new Rating("TestMoodys", "TestSandP", "TestFitch");
		Rating updatedRating = new Rating("UpdatedMoodys", "UpdatedSandP", "UpdatedFitch");
		Integer id = 1;

		// Save
		List<Rating> list = repo.findByMoodysRatingAndSandPRating(rating.getMoodysRating(), rating.getSandPRating());
		assertTrue(list.size() == 0);
		logger.info(rating.toString());
		service.post(rating);
		list = repo.findByMoodysRatingAndSandPRating(rating.getMoodysRating(), rating.getSandPRating());
		assertTrue(list.size() == 1);
		logger.info("Rating successfuly saved !");
		id = list.get(0).getId();
		rating.setId(list.get(0).getId());
		updatedRating.setId(list.get(0).getId());

		// Update
		service.put(updatedRating);
		assertEquals(updatedRating.toString(), repo.findById(id).get().toString());

		// Find
		assertNotNull(service.get(id));

		//Find All
		assertFalse(service.castList(Rating.class, service.getAll()).isEmpty());

		// Delete
		service.delete(id);
		Optional<Rating> optional = repo.findById(id);
		assertFalse(optional.isPresent());
	}
}
