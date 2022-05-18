package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameController {
    
    private static final Logger logger = LogManager.getLogger(RuleNameController.class.getName());

    @Autowired
    @Qualifier("ruleNameService")
    protected Services service;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        logger.info("Affichage de la liste de model RuleName");
        List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
        model.addAttribute("ruleName", ruleNameList);
        return "ruleName/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        logger.info("Affichage de la page d'ajout de model RuleName");
        return "ruleName/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        logger.info("Requête de validation de l'ajout d'un model RuleName");
        if(!result.hasErrors()) {
            service.post(ruleName);
            List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
            model.addAttribute("ruleName", ruleNameList);
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Affichage de la page de modification d'un model RuleName");
        RuleName ruleName = new RuleName(service.get(id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) throws NotFoundException {
        logger.info("Requête de modification d'un model RuleName");
        if(result.hasErrors()) {
            return "ruleName/update";
        }
                                                
        ruleName.setId(id);
        service.put(ruleName);
        List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
        model.addAttribute("ruleName", ruleNameList);
        return "redirect:/ruleName/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Requête de suppresion d'un model RuleName");
        service.delete(id);
        List<RuleName> ruleNameList = service.castList(RuleName.class, service.getAll());
        model.addAttribute("ruleName", ruleNameList);
        return "redirect:/ruleName/list";
    }
}
