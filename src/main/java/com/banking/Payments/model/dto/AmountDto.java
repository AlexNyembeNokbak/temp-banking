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
public class AmountDto {
	
	private Number debtorAmount;
	private String debtorCurrency;
	private Number creditorAmount;
	private String creditorCurrency;
	private String creditorCurrencyDate;
	private Number exchangeRate;
	
}
