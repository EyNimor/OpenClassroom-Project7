package com.nnk.springboot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.Services;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    private static final Logger logger = LogManager.getLogger(UserTests.class.getName());

	@Autowired
	private UserRepository repo;

	@Autowired
    @Qualifier("userService")
    protected Services service;

    @Test
    public void userTest() throws NotFoundException {
        User user = new User("TestUsername", "TestPassword", "TestFullname", "ADMIN");
		User updatedUser = new User("UpdatedUsername", "UpdatedPassword", "UpdatedFullname", "ADMIN");
		Integer id;

		// Save
		List<User> list = repo.findByUsernameAndFullname(user.getUsername(), user.getFullname());
		assertTrue(list.size() == 0);
		logger.info(user.toString());
		service.post(user);
		list = repo.findByUsernameAndFullname(user.getUsername(), user.getFullname());
		assertTrue(list.size() == 1);
		logger.info("User successfuly saved !");
		id = list.get(0).getId();
		user.setId(list.get(0).getId());
		updatedUser.setId(list.get(0).getId());

        //Password Encoder Test
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(user.getPassword(), list.get(0).getPassword()));

		// Update
		service.put(updatedUser);
        assertTrue(encoder.matches(updatedUser.getPassword(), repo.findById(id).get().getPassword()));
        updatedUser.setPassword(repo.findById(id).get().getPassword());
		assertEquals(updatedUser.toString(), repo.findById(id).get().toString());

		// Find
		assertNotNull(service.get(id));

		//Find All
		assertFalse(service.castList(User.class, service.getAll()).isEmpty());

		// Delete
		service.delete(id);
		Optional<User> optional = repo.findById(id);
		assertFalse(optional.isPresent());
    }
    
}
