package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
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
public class CurveController {
    
    private static final Logger logger = LogManager.getLogger("CurveController");

    @Autowired
    @Qualifier("curvePointService")
    protected Services service;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePoint> curvePointList = service.castList(CurvePoint.class, service.getAll());
        model.addAttribute("curvePoint", curvePointList);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            service.post(curvePoint);
            List<CurvePoint> curvePointList = service.castList(CurvePoint.class, service.getAll());
            model.addAttribute("curvePoint", curvePointList);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = new CurvePoint(service.get(id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) throws NotFoundException {
        if(result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePoint.setId(id);
        service.put(curvePoint);
        List<CurvePoint> curvePointList = service.castList(CurvePoint.class, service.getAll());
        model.addAttribute("curvePoint", curvePointList);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        service.delete(id);
        List<CurvePoint> curvePointList = service.castList(CurvePoint.class, service.getAll());
        model.addAttribute("curvePoint", curvePointList);
        return "redirect:/curvePoint/list";
    }
}
