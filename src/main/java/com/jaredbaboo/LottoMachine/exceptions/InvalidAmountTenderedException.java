package com.jaredbaboo.LottoMachine.exceptions;

public class InvalidAmountTenderedException extends Exception{
    public InvalidAmountTenderedException() {
    }

    public InvalidAmountTenderedException(String message) {
        super(message);
    }
}
