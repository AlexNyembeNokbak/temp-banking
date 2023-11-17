package com.banking.Payments.customizedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class HttpServerInternalErrorException extends HttpServerErrorException {
	
	private static final long serialVersionUID = 3L;
	private final String ERROR_MSG;
	
    public HttpServerInternalErrorException(HttpStatus statusCode, String statusText,String errorMsg) {
        super(statusCode, statusText);
        this.ERROR_MSG=errorMsg;
    }
    
    @Override
    public String getMessage() {
    	return this.ERROR_MSG;
    }

}
