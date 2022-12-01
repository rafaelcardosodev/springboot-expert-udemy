package com.github.rafaelcardosodev.rest.controller;

import com.github.rafaelcardosodev.exception.PedidoNotFoundException;
import com.github.rafaelcardosodev.exception.RegraNegocioException;
import com.github.rafaelcardosodev.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
         return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
