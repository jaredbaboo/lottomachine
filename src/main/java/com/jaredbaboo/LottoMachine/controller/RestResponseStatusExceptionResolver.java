package com.jaredbaboo.LottoMachine.controller;

import com.jaredbaboo.LottoMachine.dto.ErrorDTO;
import com.jaredbaboo.LottoMachine.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestResponseStatusExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { InvalidLottoNumbersSubmittedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleBadInputs(
            HttpServletRequest req, Exception ex) {
        String bodyOfResponse = "An error occurred with the lotto numbers submitted";
        return new ErrorDTO(bodyOfResponse, ex);
    }

    @ExceptionHandler(value
            = { InvalidTicketException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleTicketException(
            HttpServletRequest req, Exception ex) {
        String bodyOfResponse = "An error occurred with the lotto ticket request";
        return new ErrorDTO(bodyOfResponse, ex);
    }


    @ExceptionHandler(value
            = { InvalidAmountTenderedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleAccountingException(
            HttpServletRequest req, Exception ex) {
        String bodyOfResponse = "An error occurred related to the amount tendered";
        return new ErrorDTO(bodyOfResponse, ex);
    }
}
