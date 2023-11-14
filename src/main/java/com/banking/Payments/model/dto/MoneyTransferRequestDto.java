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
public class MoneyTransferRequestDto {
	
	private CreditorDto creditor;
	private String executionDate;
	private String uri;	
	private String description;
	private Number amount;
	private String currency;
	private Boolean isUrgent;
	private Boolean isInstant;
	private String feeType;
	private String feeAccountId;
	private TaxReliefDto taxRelief;
}
