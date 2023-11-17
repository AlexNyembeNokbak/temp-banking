package com.banking.Payments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.banking.Payments.model.dto.ResponseBalanceDto;
import com.banking.Payments.model.dto.ResponseTransactionsDto;
import com.banking.Payments.service.AccountService;

@RestController
public class AccountController {
	
	private static final Logger log=LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/api/gbs/banking/v4.0/accounts/{accountId}/balance")
	public ResponseEntity<ResponseBalanceDto> getAccountBalance(@PathVariable String accountId) {
		log.info("A request of account balance has arrived for the accountId : {}",accountId);
		return accountService.getAccountBalance(accountId);
	}

	@GetMapping("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
	public ResponseEntity <ResponseTransactionsDto> getAllAccountTransactions(@PathVariable String accountId, 
			                                                                  @RequestParam String fromAccountingDate,
				                                                              @RequestParam String toAccountingDate) {
		log.info("A request to find all transactions for the accountId: {} from  {} to {} has arrived.", accountId, fromAccountingDate, toAccountingDate);
		return accountService.getAllAccountTransactions(accountId,fromAccountingDate,toAccountingDate);
	}

}
