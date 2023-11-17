package com.banking.Payments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.banking.Payments.model.dto.HttpErrorRestTemplateHandler;
import com.banking.Payments.model.dto.ResponseBalanceDto;

@Service
public class AccountService {
	
	private static final Logger log=LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseEntity<ResponseBalanceDto> getAccountBalance(String accountId) {
		HttpHeaders headers=new HttpHeaders();
		headers.set("Auth-Schema", "S2S");
		headers.set("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");	
		headers.set("X-Time-Zone", "Europe/Rome");
		HttpEntity<Void> requestEntity=new HttpEntity<>(headers);
		log.info("Request entity : {}", requestEntity);
		
		ResponseEntity<ResponseBalanceDto> response=null;
		try {
			response=restTemplate.exchange("https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/balance",
					HttpMethod.GET,
					requestEntity,
					ResponseBalanceDto.class,
					accountId);
			log.info("HttpResponse: {}",response);
		}
		catch(HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			log.error(e.getMessage());
			return HttpErrorRestTemplateHandler.convertErrorMsgToResponseEntity(e.getMessage(), HttpErrorRestTemplateHandler.getGenericErrorRespBalance());
		}
				
		return response;
	}
	
	
	//+ getAllAccountTransactions(String accountId,String fromAccountingDate,String toAccountingDate) : ResponseTransactionsDto;

}
