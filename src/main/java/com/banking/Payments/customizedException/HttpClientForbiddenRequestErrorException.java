package com.banking.Payments.customizedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class HttpClientForbiddenRequestErrorException extends HttpClientErrorException {
	
	private static final long serialVersionUID = 2L;
	private final String ERROR_MSG;
	
    public HttpClientForbiddenRequestErrorException(HttpStatus statusCode, String statusText,String errorMsg) {
        super(statusCode, statusText);
        this.ERROR_MSG=errorMsg;
    }
    
    @Override
    public String getMessage() {
    	return this.ERROR_MSG;
    }
}
