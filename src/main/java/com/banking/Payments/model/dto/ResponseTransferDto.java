package com.banking.Payments.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Jacksonized
public class ResponseTransferDto extends ResponseDto {
	
	private MoneyTransferResponseDto payload;

}
