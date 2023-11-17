package com.banking.Payments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.banking.Payments.customizedException.HttpClientBadRequestErrorException;
import com.banking.Payments.model.dto.ResponseBalanceDto;

public class AccountServiceTest {
	
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private AccountService accountService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void getAccountBalanceTestBadRequest() {
		when(restTemplate.exchange(anyString(),
				any(HttpMethod.class),
				any(),
				eq(ResponseBalanceDto.class),
				anyString()))
		.thenThrow(new HttpClientBadRequestErrorException(HttpStatus.BAD_REQUEST,"","400 Bad Request: \"{<EOL><EOL>  \"status\" : \"KO\",<EOL><EOL>  \"errors\" :  [<EOL><EOL>??{<EOL><EOL>???\"code\" : \"API000\",<EOL><EOL>???\"description\" : \"it.sella.pagamenti.servizibonifico.exception.ServiziInvioBonificoSubsystemException: it.sella.pagamenti.sottosistemi.SottosistemiException: Errore tecnico CONTO 45685475:Conto 45685475 non esiste\",<EOL><EOL>???\"params\" : \"\"<EOL><EOL>??}<EOL><EOL>?],<EOL><EOL>  \"payload\": {}<EOL><EOL>}\""));
		ResponseEntity<ResponseBalanceDto> resp=accountService.getAccountBalance("14537780");
		assertEquals(HttpStatus.BAD_REQUEST,resp.getStatusCode());
	}
	
	@Test
	void getAccountBalanceTestForbiddenRequest() {
		when(restTemplate.exchange(anyString(),
				any(HttpMethod.class),
				any(),
				eq(ResponseBalanceDto.class),
				anyString()))
		.thenThrow(new HttpClientBadRequestErrorException(HttpStatus.FORBIDDEN,"","403 Forbidden Access: \"{<EOL><EOL>  \"status\" : \"KO\",<EOL><EOL>  \"errors\" :  [<EOL><EOL>??{<EOL><EOL>???\"code\" : \"API000\",<EOL><EOL>???\"description\" : \"it.sella.pagamenti.servizibonifico.exception.ServiziInvioBonificoSubsystemException: it.sella.pagamenti.sottosistemi.SottosistemiException: Errore tecnico CONTO 45685475:Conto 45685475 non esiste\",<EOL><EOL>???\"params\" : \"\"<EOL><EOL>??}<EOL><EOL>?],<EOL><EOL>  \"payload\": {}<EOL><EOL>}\""));
		ResponseEntity<ResponseBalanceDto> resp=accountService.getAccountBalance("14537780");
		assertEquals(HttpStatus.FORBIDDEN,resp.getStatusCode());
	}
	
	@Test
	void getAccountBalanceTestForbiddenRequestWithWrongExcepMsg(){
		when(restTemplate.exchange(anyString(),
				any(HttpMethod.class),
				any(),
				eq(ResponseBalanceDto.class),
				anyString()))
		.thenThrow(new HttpClientBadRequestErrorException(HttpStatus.FORBIDDEN,"","4 Forbidden Access: \"{<EOL><EOL>  \"status\" : \"KO\",<EOL><EOL>  \"errors\" :  [<EOL><EOL>??{<EOL><EOL>???\"code\" : \"API000\",<EOL><EOL>???\"description\" : \"it.sella.pagamenti.servizibonifico.exception.ServiziInvioBonificoSubsystemException: it.sella.pagamenti.sottosistemi.SottosistemiException: Errore tecnico CONTO 45685475:Conto 45685475 non esiste\",<EOL><EOL>???\"params\" : \"\"<EOL><EOL>??}<EOL><EOL>?],<EOL><EOL>  \"payload\": {}<EOL><EOL>}\""));
		ResponseEntity<ResponseBalanceDto> resp=accountService.getAccountBalance("14537780");
		assertEquals(HttpStatus.OK,resp.getStatusCode());
		assertEquals("KO",resp.getBody().getStatus());
	}
	
	@Test
	void getAccountBalanceTestForbiddenRequestWithWrongJsonMsg(){
		when(restTemplate.exchange(anyString(),
				any(HttpMethod.class),
				any(),
				eq(ResponseBalanceDto.class),
				anyString()))
		.thenThrow(new HttpClientBadRequestErrorException(HttpStatus.FORBIDDEN,"","403 Forbidden Access: \"{<EOL><EOL>  \"status}<EOL><EOL>}\""));
		ResponseEntity<ResponseBalanceDto> resp=accountService.getAccountBalance("14537780");
		assertEquals(HttpStatus.OK,resp.getStatusCode());
		assertEquals("KO",resp.getBody().getStatus());
	}

}
