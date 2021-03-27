package br.com.posweb.merceariapro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	// login
		@GetMapping("/")
		public String index() {
			return "index";
		}

		@GetMapping("/login")
		public String login() {
			return "login";
		}


}
