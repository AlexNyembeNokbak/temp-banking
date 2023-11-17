package com.banking.Payments.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banking.Payments.PaymentsApplicationTests;
import com.banking.Payments.service.AccountService;

public class AccountControllerTest extends PaymentsApplicationTests {
	
	@Mock
	private AccountService accountService;
	@InjectMocks
	private AccountController accountController;
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc=MockMvcBuilders.standaloneSetup(accountController)
				.build();
	}
	
	@Test
	void getAccountBalanceWithInvalidAccountId() throws Exception {
		String accountId="14987";
		when(accountService.getAccountBalance(anyString()))
		.thenReturn(getAccountBalanceWhithBadRequest());
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/gbs/banking/v4.0/accounts/" + accountId + "/balance"))
		.andExpect(status().is(400))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("KO"))
		.andExpect(MockMvcResultMatchers.jsonPath("errors").isNotEmpty());
	}
	
	@Test
	void getAccountBalanceWithValidAccountId() throws Exception {
		String accountId="14537780";
		when(accountService.getAccountBalance(anyString()))
		.thenReturn(getAccountBalanceWhithValidRequest());
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/gbs/banking/v4.0/accounts/" + accountId + "/balance"))
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
		.andExpect(MockMvcResultMatchers.jsonPath("errors").isEmpty());
	}
	
	@Test
	void getAllAccountTransactionsWithInvalidAccountId() throws Exception {
		String accountId="14987";
		String fromAccountingDate="26549";
		String toAccountingDate="f544";
		
		when(accountService.getAllAccountTransactions(anyString(), anyString(), anyString()))
		.thenReturn(getAllAccountTransactionsWhithBadRequest());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/gbs/banking/v4.0/accounts/" + accountId + "/transactions?fromAccountingDate="
						+fromAccountingDate+ "&toAccountingDate="+toAccountingDate))
		.andExpect(status().is(400))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("KO"))
		.andExpect(MockMvcResultMatchers.jsonPath("errors").isNotEmpty());
	}

}
