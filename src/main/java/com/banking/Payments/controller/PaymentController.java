package com.banking.Payments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.banking.Payments.model.dto.MoneyTransferRequestDto;
import com.banking.Payments.model.dto.ResponseTransferDto;
import com.banking.Payments.service.PaymentService;

import jakarta.validation.Valid;

@RestController
public class PaymentController {
	
	private static final Logger log=LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers")
	public ResponseEntity<ResponseTransferDto> createMoneyTransfer(@Valid @RequestBody MoneyTransferRequestDto input,@PathVariable String accountId){
		log.info("A request of money transfer has arrived : {} from the accountId : {}",input,accountId);
		return paymentService.createMoneyTransfer(input, accountId);
	}
}
