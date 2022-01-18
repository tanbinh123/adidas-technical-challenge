package com.adidas.subscriptionservice.exception;

import com.adidas.subscriptionservice.util.ConstantsUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles invalid arguments based on the rules written in SubscriptionDTO
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map <String, Set<String>> errorsMap =  fieldErrors.stream().collect(
                Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())
                )
        );

        return new ResponseEntity(errorsMap.isEmpty()? ex:errorsMap, headers, status);
    }

    /**
     * Handles invalid request body received by the controller
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object>
    handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(ConstantsUtil.MESSAGE, ConstantsException.INVALID_REQUEST_BODY);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
