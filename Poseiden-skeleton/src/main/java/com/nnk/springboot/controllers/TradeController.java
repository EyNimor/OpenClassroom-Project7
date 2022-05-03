package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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
public class TradeController {

    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    @Qualifier("tradeService")
    protected Services service;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<Trade> tradeList = service.castList(Trade.class, service.getAll());
        model.addAttribute("trade", tradeList);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            service.post(trade);
            List<Trade> tradeList = service.castList(Trade.class, service.getAll());
            model.addAttribute("trade", tradeList);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = new Trade(service.get(id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) throws NotFoundException {
        if(result.hasErrors()) {
            return "trade/update";
        }
                                                
        trade.setTradeId(id);
        service.put(trade);
        List<Trade> tradeList = service.castList(Trade.class, service.getAll());
        model.addAttribute("trade", tradeList);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        service.delete(id);
        List<Trade> tradeList = service.castList(Trade.class, service.getAll());
        model.addAttribute("trade", tradeList);
        return "redirect:/trade/list";
    }
}
