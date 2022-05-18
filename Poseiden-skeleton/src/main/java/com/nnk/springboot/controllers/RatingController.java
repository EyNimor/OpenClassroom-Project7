package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    private static final Logger logger = LogManager.getLogger(RatingController.class.getName());

    @Autowired
    @Qualifier("ratingService")
    protected Services service;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        logger.info("Affichage de la liste de model Rating");
        List<Rating> ratingList = service.castList(Rating.class, service.getAll());
        model.addAttribute("rating", ratingList);
        return "rating/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("Affichage de la page d'ajout de model Rating");
        return "rating/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.info("Requête de validation de l'ajout d'un model Rating");
        if(!result.hasErrors()) {
            service.post(rating);
            List<Rating> ratingList = service.castList(Rating.class, service.getAll());
            model.addAttribute("rating", ratingList);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Affichage de la page de modification d'un model Rating");
        Rating rating = new Rating(service.get(id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) throws NotFoundException {
        logger.info("Requête de modification d'un model Rating");
        if(result.hasErrors()) {
            return "curvePoint/update";
        }
                        
        rating.setId(id);
        service.put(rating);
        List<Rating> ratingList = service.castList(Rating.class, service.getAll());
        model.addAttribute("rating", ratingList);
        return "redirect:/rating/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Requête de suppresion d'un model Rating");
        service.delete(id);
        List<Rating> ratingList = service.castList(Rating.class, service.getAll());
        model.addAttribute("rating", ratingList);
        return "redirect:/rating/list";
    }
}
