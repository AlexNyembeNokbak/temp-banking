package com.banking.Payments.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Jacksonized
public class TransactionResponseDto {
	
	private String transactionId;
	private String operationId;
	private String accountingDate;
	private String valueDate;
	private TypeDto type; 
	private Number amount;
	private String currency;
	private String description;
	
}
