package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.validation.Valid;

@Controller
public class RatingController {
    
    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    @Qualifier("ratingService")
    protected Services service;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<Rating> ratingList = service.castList(Rating.class, service.getAll());
        model.addAttribute("rating", ratingList);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            service.post(rating);
            List<Rating> ratingList = service.castList(Rating.class, service.getAll());
            model.addAttribute("rating", ratingList);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = new Rating(service.get(id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) throws NotFoundException {
        if(result.hasErrors()) {
            return "curvePoint/update";
        }
                        
        rating.setId(id);
        service.put(rating);
        List<Rating> ratingList = service.castList(Rating.class, service.getAll());
        model.addAttribute("rating", ratingList);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        service.delete(id);
        List<Rating> ratingList = service.castList(Rating.class, service.getAll());
        model.addAttribute("rating", ratingList);
        return "redirect:/rating/list";
    }
}
