package com.banking.Payments.model.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpErrorRestTemplateHandler {
	
	private static final Logger log=LoggerFactory.getLogger(HttpErrorRestTemplateHandler.class);
	public static final String END_OF_LINE1= "<EOL>";
	public static final String END_OF_LINE2= "\\?";
	public static final int BAD_REQUEST=400;
	public static final int UNAUTHORIZED=401;
	public static final int FORBIDDEN_ACCESS=403;
	public static final int NOT_FOUND=404;
	public static final int METHOD_NOT_ALLOWED=405;
	public static final int UNSUPPORTED_MEDIA_TYPE=415;
	public static final int TOO_MANY_REQUESTS=429;
	public static final int INTERNAL_SERVER_ERROR=500;
	public static final int BAD_GATEWAY=502;
	public static final int GATEWAY_TIMEOUT=503;
	
	
	/*public static ResponseEntity<ResponseTransferDto> convertErrorMsgToResponseTransfer(String errorMsg) {
		ResponseTransferDto respTransfer=null;
		if (errorMsg==null) {
			respTransfer=getGenericErrorRespTransfer();
			return ResponseEntity.ok(respTransfer);
		}
		String errCode=errorMsg.substring(0, 3);
		log.info("ErrCode: {}",errCode);
		
		String parsedErrMsg=errorMsg.replaceAll(HttpErrorRestTemplateHandler.END_OF_LINE1, "");
		String finalParsedErrMsg=parsedErrMsg.replaceAll(HttpErrorRestTemplateHandler.END_OF_LINE2, " ");
		log.info("Final parsed error msg: {}",finalParsedErrMsg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRespTransfer=getJsonRespTransfer(finalParsedErrMsg);
		try {
			respTransfer=objectMapper.readValue(jsonRespTransfer, ResponseTransferDto.class);
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
			respTransfer=getGenericErrorRespTransfer();
			return ResponseEntity.ok(respTransfer);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			respTransfer=getGenericErrorRespTransfer();
			return ResponseEntity.ok(respTransfer);
		}
		
		log.info("respTransfer: {}",respTransfer);
		Integer parsedErrCode=null;
		try {
			parsedErrCode=Integer.valueOf(errCode);
		}catch(NumberFormatException e) {
			log.error(e.getMessage());
			return ResponseEntity.ok(respTransfer);
		}
		
		if (parsedErrCode<BAD_REQUEST) {
			return ResponseEntity.ok(respTransfer);
		}
				
		return ResponseEntity.status(parsedErrCode)
				.body(respTransfer);
	}*/
	
	public static <E> ResponseEntity<E> convertErrorMsgToResponseEntity(String errorMsg,E genericErrorResp) {
		E respTransfer=null;
		if (errorMsg==null) {
			return ResponseEntity.ok(genericErrorResp);
		}
		String errCode=errorMsg.substring(0, 3);
		log.info("ErrCode: {}",errCode);
		
		String parsedErrMsg=errorMsg.replaceAll(HttpErrorRestTemplateHandler.END_OF_LINE1, "");
		String finalParsedErrMsg=parsedErrMsg.replaceAll(HttpErrorRestTemplateHandler.END_OF_LINE2, " ");
		log.info("Final parsed error msg: {}",finalParsedErrMsg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonRespTransfer=getJsonRespTransfer(finalParsedErrMsg);
		try {
			respTransfer=(E) objectMapper.readValue(jsonRespTransfer, genericErrorResp.getClass());
		}catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return ResponseEntity.ok(genericErrorResp);
		}
		
		log.info("respTransfer: {}",respTransfer);
		Integer parsedErrCode=null;
		try {
			parsedErrCode=Integer.valueOf(errCode);
		}catch(NumberFormatException e) {
			log.error(e.getMessage());
			return ResponseEntity.ok(genericErrorResp);
		}
		
		if (parsedErrCode<BAD_REQUEST) {
			return ResponseEntity.ok(genericErrorResp);
		}
				
		return ResponseEntity.status(parsedErrCode)
				.body(respTransfer);
	}
	
	private static String getJsonRespTransfer(String errorMsg) {
		int beginJsonIndex=errorMsg.indexOf('"');
		String jsonErrMsg=errorMsg.substring(beginJsonIndex+1,errorMsg.length()-1);
		log.info("jsonErrMsg: {}",jsonErrMsg);
		return jsonErrMsg;
	}
	
	public static ResponseTransferDto getGenericErrorRespTransfer(){
		return ResponseTransferDto.builder()
				.status("KO")
				.errors(new ErrorDto[] {ErrorDto.builder().code("").description("generic error").params(null).build()})
				.payload(null)
				.build();
	}  
	
	public static ResponseBalanceDto getGenericErrorRespBalance() {
		return ResponseBalanceDto.builder()
				.status("KO")
				.errors(new ErrorDto[] {ErrorDto.builder().code("").description("generic error").params(null).build()})
				.payload(null)
				.build();
	}
		
}
