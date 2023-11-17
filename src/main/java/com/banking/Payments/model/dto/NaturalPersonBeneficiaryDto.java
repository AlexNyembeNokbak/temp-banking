package com.banking.Payments.model.dto;

import jakarta.validation.constraints.NotNull;
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
public class NaturalPersonBeneficiaryDto {
	@NotNull(message="fiscalCode1 may not be null")
	private String fiscalCode1;
	private String fiscalCode2;
	private String fiscalCode3;
	private String fiscalCode4;
	private String fiscalCode5;
}
