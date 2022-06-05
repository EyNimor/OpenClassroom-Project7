package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByMoodysRatingAndSandPRating(String moodysRating, String sandPRating);

}
