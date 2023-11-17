package com.banking.Payments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import com.banking.Payments.model.dto.MoneyTransferRequestDto;
import com.banking.Payments.model.dto.ResponseTransferDto;
import com.banking.Payments.model.dto.HttpErrorRestTemplateHandler;
import jakarta.validation.Valid;

@Service
@Validated
public class PaymentService {
	
	private static final Logger log=LoggerFactory.getLogger(PaymentService.class);
	@Autowired
	private RestTemplate restTemplate; 
	
	public ResponseEntity<ResponseTransferDto> createMoneyTransfer(@Valid MoneyTransferRequestDto request,String accountId) {
		HttpHeaders headers=new HttpHeaders();
		headers.set("Auth-Schema", "S2S");
		headers.set("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Time-Zone", "Europe/Rome");
		HttpEntity<MoneyTransferRequestDto> httpRequest=new HttpEntity<>(request,headers);
		log.info("HttpRequest: {}",httpRequest);
		
		String url="https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"
				+ accountId
				+ "/payments/money-transfers";
		log.info("Final url: {}",url);
		
		ResponseEntity<ResponseTransferDto> httpResponse=null;
		try {
			httpResponse=restTemplate.exchange(url,
					HttpMethod.POST,
					httpRequest,
					ResponseTransferDto.class);
			log.info("HttpResponse: {}",httpResponse);
		}
		catch(HttpClientErrorException | HttpServerErrorException | UnknownHttpStatusCodeException e) {
			log.error(e.getMessage());
			return HttpErrorRestTemplateHandler.convertErrorMsgToResponseEntity(e.getMessage(), HttpErrorRestTemplateHandler.getGenericErrorRespTransfer());
		}
				
		return httpResponse;
	}

}
