package com.coderscampus.assignment13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;	
	@Autowired
	private UserService userService;

	public Account saveAccount(Account account) {
		return accountRepo.save(account);
	}
	
	public List<Account> findAllAccountsByUserId(Long userId) {
		List<Account> accounts = userService.findById(userId).getAccounts();
		return accounts;
	}


	public Account findById(Long accountId) {
		Optional<Account> accountOpt = accountRepo.findById(accountId);
		return accountOpt.orElse(new Account());
	}


	public void createAccountByUserId(Long userId) {
		Account account = new Account();
		account.getUsers().add(userService.findById(userId));
		userService.findById(userId).getAccounts().add(account);
		Integer accountNumber = userService.findById(userId).getAccounts().size();
		account.setAccountName("Account" + " #" + accountNumber);
		accountRepo.save(account);
		
	}

}
