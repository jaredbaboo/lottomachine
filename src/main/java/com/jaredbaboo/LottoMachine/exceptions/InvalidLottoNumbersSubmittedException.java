package com.jaredbaboo.LottoMachine.exceptions;

public class InvalidLottoNumbersSubmittedException extends Exception{
    public InvalidLottoNumbersSubmittedException() {
    }

    public InvalidLottoNumbersSubmittedException(String message) {
        super(message);
    }
}
