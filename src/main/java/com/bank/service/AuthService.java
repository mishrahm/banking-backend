package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;
import com.bank.security.JwtUtil;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository; 
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	//register 
	public void register(String name, String email, String password) {
		
		// check if email already exists
		if(userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already registered");
		}
		
		//create user
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		
		// 🔐 PASSWORD HASHING (BCrypt)
		user.setPassword(passwordEncoder.encode(password));
		
		 // ✅ SAVE USER FIRST
        User savedUser = userRepository.save(user);
		
		//create bank account for user
		Account account = new Account();
		account.setBalance(0.0);
		account.setUser(savedUser);
		
		accountRepository.save(account);
	}
	
	//login 
	public String login(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Invalid email or password"));
		//password verification
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid email or password");
		}
		return jwtUtil.generateToken(user.getEmail());
		
	}
	
	
}
