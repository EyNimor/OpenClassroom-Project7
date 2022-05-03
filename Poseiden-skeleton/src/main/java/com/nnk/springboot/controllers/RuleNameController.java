package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameController {
    
    private static final Logger logger = LogManager.getLogger("RatingController");

    @Autowired
    @Qualifier("ruleNameService")
    protected Services service;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
        model.addAttribute("ruleName", ruleNameList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            service.post(ruleName);
            List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
            model.addAttribute("ruleName", ruleNameList);
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = new RuleName(service.get(id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) throws NotFoundException {
        if(result.hasErrors()) {
            return "ruleName/update";
        }
                                                
        ruleName.setId(id);
        service.put(ruleName);
        List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
        model.addAttribute("ruleName", ruleNameList);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        service.delete(id);
        List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
        model.addAttribute("ruleName", ruleNameList);
        return "redirect:/ruleName/list";
    }
}
