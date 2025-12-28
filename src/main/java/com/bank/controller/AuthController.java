package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authservice;
	
	@PostMapping("/register")
	public String register(
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password) {
		
		authservice.register(name, email, password);
		return "User register successfully";		
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam String email, 
			@RequestParam String password) {
		
		String token = authservice.login(email, password);
		return token; //Later we’ll return JSON.
	}
	
}
