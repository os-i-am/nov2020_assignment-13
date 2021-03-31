package com.coderscampus.assignment13.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.AddressService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		
		model.put("user", new User());
		
		return "register";
	}
	
	@PostMapping("/register")
	public String postCreateUser (User user) {
		System.out.println(user);
		userService.saveNewUser(user);
		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();	
		model.put("users", users);		
		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Address address = addressService.findById(userId);
		List<Account> accounts = accountService.findAllAccountsByUserId(userId);
		model.put("user", user);
		model.put ("address", address);
		model.put ("accounts", accounts);
		return "user";
	}
	
	@PostMapping("/users/{userId}")
	public String postOneUser (User user, Address address, ArrayList<Account> accounts, @PathVariable Long userId) {
//		addressService.saveAddress(address);
//		accountService.saveAccount(account);
		userService.saveUserDetails(user, address, accounts, userId);
		return "redirect:/users/"+user.getUserId();
	}
	
	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}
	
	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getOneAccount (ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
		Account account = accountService.findById(accountId);
		model.put("account", account);
		return "account";
	}
	
	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String updateAccount (ModelMap model, Account account, @PathVariable Long userId) {
		accountService.saveAccount(account);
		return "redirect:/users/{userId}";
	}
	
	@PostMapping("/users/{userId}/accounts")
	public String postCreateAccount (@PathVariable Long userId) {
		accountService.createAccountByUserId(userId);
		return "redirect:/users/{userId}";
	}
}
