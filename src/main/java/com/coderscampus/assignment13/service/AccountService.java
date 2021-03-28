package com.coderscampus.assignment13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;	
	@Autowired
	private UserService userService;

	public Account saveAccount(Account account, User user) {
		if (user.getAccounts().contains(account) == false) {
			user.getAccounts().add(account);
			account.getUsers().add(user);
		}
		return accountRepo.save(account);
	}


	public Account findById(Long accountId) {
		Optional<Account> accountOpt = accountRepo.findById(accountId);
		return accountOpt.orElse(new Account());
	}


	public void createAccountByUserId(Long userId) {
		Account account = new Account();
		account.getUsers().add(userService.findById(userId));
		account.setAccountName("new Account");
		userService.findById(userId).getAccounts().add(account);
		accountRepo.save(account);
		
	}

}
