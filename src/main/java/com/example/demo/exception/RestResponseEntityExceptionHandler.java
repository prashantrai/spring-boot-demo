package com.example.demo.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * Reference: https://www.baeldung.com/exception-handling-for-rest-with-spring
 * 
 * https://www.toptal.com/java/spring-boot-rest-api-error-handling
 * 
 * https://mkyong.com/spring-boot/spring-rest-error-handling-example/
 * https://reflectoring.io/spring-boot-exception-handling/
 * https://www.springboottutorial.com/spring-boot-exception-handling-for-rest-services
 * */


/**
 * A controller advice allows us to intercept and modify the return values of controller methods, 
 * in our case to handle exceptions. 
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
     * Handle custom exception 
     */
	@ExceptionHandler(APIException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(RuntimeException ex, WebRequest request) {

        ApiErrorResponse errors = new ApiErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
    
    /* Let Spring BasicErrorController handle the exception,
     *  we just override the status code
     */
    //@ExceptionHandler(APIException.class)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
    
    
	/*@ExceptionHandler(value  = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }*/
    
}


