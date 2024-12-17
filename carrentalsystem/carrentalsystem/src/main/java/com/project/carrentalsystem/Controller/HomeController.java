package com.project.carrentalsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	 public String homePage() {
	     return "index"; 
	}

	@GetMapping("/registerEmployee")
	 public String registerEmployee() {
	     return "registerEmployee"; 
	}
}
