package com.banking.Payments;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import com.banking.Payments.enumeration.ResponseStatus;
import com.banking.Payments.model.dto.AccountTransactionsResponseDto;
import com.banking.Payments.model.dto.HttpErrorRestTemplateHandler;
import com.banking.Payments.model.dto.MoneyTransferRequestDto;
import com.banking.Payments.model.dto.MoneyTransferResponseDto;
import com.banking.Payments.model.dto.ResponseBalanceDto;
import com.banking.Payments.model.dto.ResponseTransactionsDto;
import com.banking.Payments.model.dto.ResponseTransferDto;
import com.banking.Payments.model.dto.TransactionResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=PaymentsApplication.class)
@WebAppConfiguration
public class PaymentsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	protected String getMoneyTransferUrl() {
		String accountId="14537780";
		String url="https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/"
				+ accountId
				+ "/payments/money-transfers";
		return url;
	}
	
	protected HttpEntity<MoneyTransferRequestDto> getMoneyTransferHttpHeaders() {
		HttpHeaders headers=new HttpHeaders();
		headers.set("Auth-Schema", "S2S");
		headers.set("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Time-Zone", "Europe/Rome");
		MoneyTransferRequestDto request=getMoneyTransferRequest();
		HttpEntity<MoneyTransferRequestDto> httpRequest=new HttpEntity<>(request,headers);
		return httpRequest;
	}
	
	protected MoneyTransferRequestDto getMoneyTransferRequest() {
		MoneyTransferRequestDto request=null;
		String json="{\n"
				+ "  \"creditor\": {\n"
				+ "    \"name\": \"John Doe\",\n"
				+ "    \"account\": {\n"
				+ "      \"accountCode\": \"IT23A0336844430152923804660\",\n"
				+ "      \"bicCode\": \"SELBIT2BXXX\"\n"
				+ "    },\n"
				+ "    \"address\": {\n"
				+ "      \"address\": null,\n"
				+ "      \"city\": null,\n"
				+ "      \"countryCode\": null\n"
				+ "    }\n"
				+ "  },\n"
				+ "  \"executionDate\": \"2019-04-01\",\n"
				+ "  \"uri\": \"REMITTANCE_INFORMATION\",\n"
				+ "  \"description\": \"Payment invoice 75/2017\",\n"
				+ "  \"amount\": 800,\n"
				+ "  \"currency\": \"EUR\",\n"
				+ "  \"isUrgent\": false,\n"
				+ "  \"isInstant\": false,\n"
				+ "  \"feeType\": \"SHA\",\n"
				+ "  \"feeAccountId\": \"45685475\",\n"
				+ "  \"taxRelief\": {\n"
				+ "    \"taxReliefId\": \"L449\",\n"
				+ "    \"isCondoUpgrade\": false,\n"
				+ "    \"creditorFiscalCode\": \"56258745832\",\n"
				+ "    \"beneficiaryType\": \"NATURAL_PERSON\",\n"
				+ "    \"naturalPersonBeneficiary\": {\n"
				+ "      \"fiscalCode1\": \"MRLFNC81L04A859L\",\n"
				+ "      \"fiscalCode2\": null,\n"
				+ "      \"fiscalCode3\": null,\n"
				+ "      \"fiscalCode4\": null,\n"
				+ "      \"fiscalCode5\": null\n"
				+ "    },\n"
				+ "    \"legalPersonBeneficiary\": {\n"
				+ "      \"fiscalCode\": null,\n"
				+ "      \"legalRepresentativeFiscalCode\": null\n"
				+ "    }\n"
				+ "  }\n"
				+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			request=objectMapper.readValue(json, MoneyTransferRequestDto.class);
		} catch (JsonMappingException e) {
			return null;
		} catch (JsonProcessingException e) {
			return null;
		}
		return request;
	}
	
	public ResponseEntity<ResponseTransferDto> getMoneyTransferBadResponseTest() {
		ResponseTransferDto resp=ResponseTransferDto.builder()
				.errors(null)
				.payload(new MoneyTransferResponseDto())
				.status(ResponseStatus.KO.name())
				.build();
		return ResponseEntity.status(HttpErrorRestTemplateHandler.BAD_REQUEST)
				.body(resp);
	}
	
	public ResponseEntity<ResponseBalanceDto> getAccountBalanceWhithBadRequest() {
		return ResponseEntity.status(HttpErrorRestTemplateHandler.BAD_REQUEST)
				.body(HttpErrorRestTemplateHandler.getGenericErrorRespBalance());
	} 
	
	public ResponseEntity<ResponseTransactionsDto> getAllAccountTransactionsWhithBadRequest() {
		return ResponseEntity.status(HttpErrorRestTemplateHandler.BAD_REQUEST)
				.body(HttpErrorRestTemplateHandler.getGenericErrorRespTransactions());
	}
	
	public ResponseEntity<ResponseBalanceDto> getAccountBalanceWhithValidRequest() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(getResponseBalance());
	}
	
	public ResponseBalanceDto getResponseBalance(){
		String json="{\n"
				+ "    \"status\": \"OK\",\n"
				+ "    \"errors\": null,\n"
				+ "    \"payload\": {\n"
				+ "        \"date\": \"2023-11-17\",\n"
				+ "        \"balance\": 99992.48,\n"
				+ "        \"availableBalance\": 99992.48,\n"
				+ "        \"currency\": \"EUR\"\n"
				+ "    }\n"
				+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseBalanceDto resp=null;
		try {
			resp=objectMapper.readValue(json,ResponseBalanceDto.class);
		}catch (JsonMappingException e) {
			return null;
		} catch (JsonProcessingException e) {
			return null;
		}
		return resp;
	}
	
	public ResponseEntity<ResponseTransferDto> getMoneyTransferRespWithBadRequest() {
		return ResponseEntity.status(HttpErrorRestTemplateHandler.BAD_REQUEST)
				.body(HttpErrorRestTemplateHandler.getGenericErrorRespTransfer());
	}
	
	public ResponseEntity<ResponseTransferDto> getMoneyTransferRespWithValidRequest(){
		return ResponseEntity.status(HttpStatus.OK)
				.body(getMoneyTransferRespToValidRequest());
	}
	
	public ResponseTransferDto getMoneyTransferRespToValidRequest() {
		String json="{\n"
				+ "	\"status\": \"OK\",\n"
				+ "	\"errors\": null,\n"
				+ "	\"payload\": {\n"
				+ "		\"moneyTransferId\": null,\n"
				+ "		\"status\": null,\n"
				+ "		\"direction\": null,\n"
				+ "		\"creditor\": null,\n"
				+ "		\"debtor\": null,\n"
				+ "		\"cro\": null,\n"
				+ "		\"trn\": null,\n"
				+ "		\"uri\": null,\n"
				+ "		\"description\": null,\n"
				+ "		\"createdDatetime\": null,\n"
				+ "		\"accountedDatetime\": null,\n"
				+ "		\"debtorValueDate\": null,\n"
				+ "		\"creditorValueDate\": null,\n"
				+ "		\"amount\": null,\n"
				+ "		\"isUrgent\": null,\n"
				+ "		\"isInstant\": null,\n"
				+ "		\"feeType\": null,\n"
				+ "		\"feeAccountID\": null,\n"
				+ "		\"fees\": null,\n"
				+ "		\"hasTaxRelief\": null\n"
				+ "	}\n"
				+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseTransferDto resp=null;
		try {
			resp=objectMapper.readValue(json,ResponseTransferDto.class);
		}catch (JsonProcessingException e) {
			return null;
		}
		return resp;
	}
	
	public ResponseEntity <ResponseTransactionsDto> getAllAccountTransactionsWithValidRequest() {
		String json="{\n"
				+ "  \"list\": [\n"
				+ "    {\n"
				+ "      \"transactionId\": \"1331714087\",\n"
				+ "      \"operationId\": \"00000000273015\",\n"
				+ "      \"accountingDate\": \"2019-04-01\",\n"
				+ "      \"valueDate\": \"2019-04-01\",\n"
				+ "      \"type\": {\n"
				+ "        \"enumeration\": \"GBS_TRANSACTION_TYPE\",\n"
				+ "        \"value\": \"GBS_TRANSACTION_TYPE_0023\"\n"
				+ "      },\n"
				+ "      \"amount\": -800,\n"
				+ "      \"currency\": \"EUR\",\n"
				+ "      \"description\": \"BA JOHN DOE PAYMENT INVOICE 75/2017\"\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"transactionId\": \"1331714088\",\n"
				+ "      \"operationId\": \"00000000273015\",\n"
				+ "      \"accountingDate\": \"2019-04-01\",\n"
				+ "      \"valueDate\": \"2019-04-01\",\n"
				+ "      \"type\": {\n"
				+ "        \"enumeration\": \"GBS_TRANSACTION_TYPE\",\n"
				+ "        \"value\": \"GBS_TRANSACTION_TYPE_0015\"\n"
				+ "      },\n"
				+ "      \"amount\": -1,\n"
				+ "      \"currency\": \"EUR\",\n"
				+ "      \"description\": \"CO MONEY TRANSFER FEES\"\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		AccountTransactionsResponseDto allTransactionsResp=null;
		ResponseTransactionsDto responseTransactionsDto=null;
		try {
			allTransactionsResp=objectMapper.readValue(json,AccountTransactionsResponseDto.class);
		}catch (JsonProcessingException e) {
			responseTransactionsDto=ResponseTransactionsDto.builder()
					.status("OK")
					.errors(null)
					.payload(getAccountTransactionsRespWithValidRequest())
					.build();
			return ResponseEntity.ok(responseTransactionsDto);
		}
		responseTransactionsDto=ResponseTransactionsDto.builder()
				.status("OK")
				.errors(null)
				.payload(allTransactionsResp)
				.build();
		return ResponseEntity.ok(responseTransactionsDto);
	}
	
	public AccountTransactionsResponseDto getAccountTransactionsRespWithValidRequest() {
		return AccountTransactionsResponseDto.builder()
		.list(new TransactionResponseDto[0])
		.build();
	}
	
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper=new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	public MoneyTransferRequestDto getValidMoneyTransferRequest() {
		String json="{\n"
				+ "  \"creditor\": {\n"
				+ "    \"name\": \"John Doe\",\n"
				+ "    \"account\": {\n"
				+ "      \"accountCode\": \"IT23A0336844430152923804660\",\n"
				+ "      \"bicCode\": \"SELBIT2BXXX\"\n"
				+ "    },\n"
				+ "    \"address\": {\n"
				+ "      \"address\": null,\n"
				+ "      \"city\": null,\n"
				+ "      \"countryCode\": null\n"
				+ "    }\n"
				+ "  },\n"
				+ "  \"executionDate\": \"2019-04-01\",\n"
				+ "  \"uri\": \"REMITTANCE_INFORMATION\",\n"
				+ "  \"description\": \"Payment invoice 75/2017\",\n"
				+ "  \"amount\": 800,\n"
				+ "  \"currency\": \"EUR\",\n"
				+ "  \"isUrgent\": false,\n"
				+ "  \"isInstant\": false,\n"
				+ "  \"feeType\": \"SHA\",\n"
				+ "  \"feeAccountId\": \"45685475\",\n"
				+ "  \"taxRelief\": {\n"
				+ "    \"taxReliefId\": \"L449\",\n"
				+ "    \"isCondoUpgrade\": false,\n"
				+ "    \"creditorFiscalCode\": \"56258745832\",\n"
				+ "    \"beneficiaryType\": \"NATURAL_PERSON\",\n"
				+ "    \"naturalPersonBeneficiary\": {\n"
				+ "      \"fiscalCode1\": \"MRLFNC81L04A859L\",\n"
				+ "      \"fiscalCode2\": null,\n"
				+ "      \"fiscalCode3\": null,\n"
				+ "      \"fiscalCode4\": null,\n"
				+ "      \"fiscalCode5\": null\n"
				+ "    },\n"
				+ "    \"legalPersonBeneficiary\": {\n"
				+ "      \"fiscalCode\": null,\n"
				+ "      \"legalRepresentativeFiscalCode\": null\n"
				+ "    }\n"
				+ "  }\n"
				+ "}";
		ObjectMapper objectMapper = new ObjectMapper();
		MoneyTransferRequestDto req=null;
		try {
			req=objectMapper.readValue(json,MoneyTransferRequestDto.class);
		}catch (JsonProcessingException e) {
			return null;
		}
		return req;
	}

}
