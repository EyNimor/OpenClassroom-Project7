package com.nnk.springboot.controllers;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;
import com.nnk.springboot.domain.User;
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
@ExcludeFromJacocoGeneratedReport
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());

    @Autowired
    @Qualifier("userService")
    protected Services service;

    @RequestMapping("/user/list")
    public String home(Model model) {
        logger.info("Affichage de la liste de model User");
        List<User> userList = service.castList(User.class, service.getAll());
        model.addAttribute("users", userList);
        return "user/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/add")
    public String addUser(User bid) {
        logger.info("Affichage de la page d'ajout de model User");
        return "user/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        logger.info("Requête de validation de l'ajout d'un model User");
        if (!result.hasErrors()) {
            service.post(user);
            List<User> userList = service.castList(User.class, service.getAll());
            model.addAttribute("users", userList);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("Affichage de la page de modification d'un model User");
        User user = new User(service.get(id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) throws NotFoundException {
        logger.info("Requête de modification d'un model User");
        if (result.hasErrors()) {
            return "user/update";
        }

        user.setId(id);
        service.put(user);
        List<User> userList = service.castList(User.class, service.getAll());
        model.addAttribute("users", userList);
        return "redirect:/user/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.info("Requête de suppresion d'un model User");
        service.delete(id);
        List<User> userList = service.castList(User.class, service.getAll());
        model.addAttribute("users", userList);
        return "redirect:/user/list";
    }
}
