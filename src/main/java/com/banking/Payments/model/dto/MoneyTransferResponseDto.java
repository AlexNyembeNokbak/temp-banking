package com.banking.Payments.model.dto;

import java.util.Date;
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
public class MoneyTransferResponseDto {
	
	private String moneyTransferId;
	private String status;
	private String direction;
	private CreditorDto creditor;
	private DebtorDto debtor;
	private String cro;
	private String trn;
	private String uri;
	private String description;
	private Date createdDatetime;
	private Date accountedDatetime;
	private String debtorValueDate;
	private String creditorValueDate;
	private AmountDto amount;
	private Boolean isUrgent;
	private Boolean isInstant;
	private String feeType;
	private String feeAccountID;
	private FeeDto[] fees;
	private Boolean hasTaxRelief;
	
}
