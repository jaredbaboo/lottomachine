package com.jaredbaboo.LottoMachine.exceptions;

public class InvalidTicketException extends Exception{
    public InvalidTicketException() {
    }

    public InvalidTicketException(String message) {
        super(message);
    }
}
