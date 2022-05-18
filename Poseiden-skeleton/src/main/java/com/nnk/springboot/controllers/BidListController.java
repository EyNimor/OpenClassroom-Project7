package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
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
public class BidListController {
    
    private static final Logger logger = LogManager.getLogger(BidListController.class.getName());

    @Autowired
    @Qualifier("bidService")
    protected Services service;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        logger.info("Affichage de la liste de model BidList");
        List<BidList> bidList = service.castList(BidList.class, service.getAll());
        model.addAttribute("bidList", bidList);
        return "bidList/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("Affichage de la page d'ajout de model BidList");
        return "bidList/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.info("Requête de validation de l'ajout d'un model BidList");
        if(!result.hasErrors()) {
            service.post(bid);
            List<BidList> bidList = service.castList(BidList.class, service.getAll());
            model.addAttribute("bidList", bidList);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Affichage de la page de modification d'un model BidList");
        BidList bid = new BidList(service.get(id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bid,
                             BindingResult result, Model model) throws NotFoundException {
        logger.info("Requête de modification d'un model BidList");
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bid.setBidListId(id);
        service.put(bid);
        List<BidList> bidList = service.castList(BidList.class, service.getAll());
        model.addAttribute("bidList", bidList);
        return "redirect:/bidList/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("Requête de suppresion d'un model BidList");
        service.delete(id);
        List<BidList> bidList = service.castList(BidList.class, service.getAll());
        model.addAttribute("bidList", bidList);
        return "redirect:/bidList/list";
    }
}
