package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.UserRepository;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository; 
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void register(String name, String email, String password) {
		
		// check if email already exists
		if(userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already registered");
		}
		
		//create user
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		
		 // ✅ SAVE USER FIRST
        User savedUser = userRepository.save(user);
		
		// 🔐 PASSWORD HASHING (BCrypt)
		user.setPassword(passwordEncoder.encode(password));
		
		//create bank account for user
		Account account = new Account();
		account.setBalance(0.0);
		account.setUser(user);
		
		accountRepository.save(account);
	}
}
