package com.nnk.springboot.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service("ratingService")
public class RatingService implements Services {

    private static final Logger logger = LogManager.getLogger("RatingService");

    @Autowired
    protected RatingRepository ratingRepo;

    @Override
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        return rawCollection.stream().map(o -> clazz.cast(o)).collect(Collectors.toList());
    }

    @Override
    public Collection<?> getAll() {
        List<Rating> rating = ratingRepo.findAll();
        return rating;
    }

    @Override
    public Object get(Integer id) {
        Rating rating = ratingRepo.findById(id).orElse(null);
        return rating;
    }

    @Override
    public void post(Object objectToPost) {
        Rating rating = new Rating(objectToPost);
        ratingRepo.saveAndFlush(rating);
    }

    @Override
    public void put(Object objectToPut) throws NotFoundException {
        Rating ratingToPut = new Rating(objectToPut);
        Rating ancientRating = ratingRepo.findById(ratingToPut.getId()).orElseThrow(NotFoundException::new);
        ancientRating.setMoodysRating(ratingToPut.getMoodysRating());
        ancientRating.setSandPRating(ratingToPut.getSandPRating());
        ancientRating.setFitchRating(ratingToPut.getFitchRating());
        ratingRepo.saveAndFlush(ancientRating);
    }

    @Override
    public void delete(Integer id) {
        ratingRepo.deleteById(id);
    }
    
}
