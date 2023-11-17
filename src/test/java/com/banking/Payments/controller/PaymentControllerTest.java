package com.banking.Payments.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banking.Payments.PaymentsApplicationTests;
import com.banking.Payments.model.dto.MoneyTransferRequestDto;
import com.banking.Payments.service.PaymentService;

public class PaymentControllerTest extends PaymentsApplicationTests {
	
	@Mock
	private PaymentService paymentService;
	@InjectMocks
	private PaymentController paymentController;
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc=MockMvcBuilders.standaloneSetup(paymentController)
				.build();
	}
	
	@Test
	void createMoneyTransferWithInvalidRequest() throws Exception {
		String accountId="14987";
		MoneyTransferRequestDto req=new MoneyTransferRequestDto();
		
		when(paymentService.createMoneyTransfer(any(), anyString()))
		.thenReturn(getMoneyTransferRespWithBadRequest());
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/gbs/banking/v4.0/accounts/" + accountId + "/payments/money-transfers")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapToJson(req)))
		.andExpect(status().is(400))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("KO"))
		.andExpect(MockMvcResultMatchers.jsonPath("errors").isNotEmpty());
	}
	
	@Test
	void createMoneyTransferWithValidRequest() throws Exception {
		String accountId="14537780";
		MoneyTransferRequestDto req=getValidMoneyTransferRequest();
		
		when(paymentService.createMoneyTransfer(any(), anyString()))
		.thenReturn(getMoneyTransferRespWithValidRequest());
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/gbs/banking/v4.0/accounts/" + accountId + "/payments/money-transfers")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapToJson(req)))
		.andExpect(status().is(200))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
		.andExpect(MockMvcResultMatchers.jsonPath("errors").isEmpty());
	}
}
