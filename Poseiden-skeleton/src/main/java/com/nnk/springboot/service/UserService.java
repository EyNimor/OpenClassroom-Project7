package com.nnk.springboot.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service("userService")
public class UserService implements Services {

    private static final Logger logger = LogManager.getLogger("tradeService");

    @Autowired
    protected UserRepository userRepo;

    @Override
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        return rawCollection.stream().map(o -> clazz.cast(o)).collect(Collectors.toList());
    }

    @Override
    public Collection<?> getAll() {
        List<User> users = userRepo.findAll();
        return users;
    }

    @Override
    public Object get(Integer id) {
        User user = userRepo.findById(id).orElse(null);
        return user;
    }

    @Override
    public void post(Object objectToPost) {
        User user = new User(objectToPost);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.saveAndFlush(user);
    }

    @Override
    public void put(Object objectToPut) throws NotFoundException {
        User userToPut = new User(objectToPut);
        User ancientUser = userRepo.findById(userToPut.getId()).orElseThrow(NotFoundException::new);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ancientUser.setUsername(userToPut.getUsername());
        ancientUser.setPassword(encoder.encode(userToPut.getPassword()));
        ancientUser.setFullname(userToPut.getFullname());
        ancientUser.setRole(userToPut.getRole());
        userRepo.saveAndFlush(ancientUser);
    }

    @Override
    public void delete(Integer id) {
        userRepo.deleteById(id);
    }
    
}
