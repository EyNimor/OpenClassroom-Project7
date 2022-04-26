package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
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
public class BidListController {
    
    private static final Logger logger = LogManager.getLogger("BidController");

    @Autowired
    @Qualifier("bidService")
    protected Services service;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidList> bidList = service.castList(BidList.class, service.getAll());
        model.addAttribute("bidList", bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            service.post(bid);
            List<BidList> bidList = service.castList(BidList.class, service.getAll());
            model.addAttribute("bidList", bidList);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bid = new BidList(service.get(id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bid,
                             BindingResult result, Model model) throws NotFoundException {
        if (result.hasErrors()) {
            return "bidList/update";
        }

        bid.setBidListId(id);
        service.put(bid);
        List<BidList> bidList = service.castList(BidList.class, service.getAll());
        model.addAttribute("bidList", bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        service.delete(id);
        List<BidList> bidList = service.castList(BidList.class, service.getAll());
        model.addAttribute("bidList", bidList);
        return "redirect:/bidList/list";
    }
}
