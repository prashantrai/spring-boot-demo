package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class APIException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public APIException() {
        super();
    }
    public APIException(String message, Throwable cause) {
        super(message, cause);
    }
    public APIException(String message) {
        super(message);
    }
    public APIException(Throwable cause) {
        super(cause);
    }
}
