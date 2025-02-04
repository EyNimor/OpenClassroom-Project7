package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;

@Controller
@ExcludeFromJacocoGeneratedReport
public class HomeController {

	private static final Logger logger = LogManager.getLogger(HomeController.class.getName());

	@RequestMapping("/")
	public String home(Model model) {
		logger.info("Affichage de la Page d'accueil");
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}

}
