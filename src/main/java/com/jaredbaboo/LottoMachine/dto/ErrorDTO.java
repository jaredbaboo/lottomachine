package com.jaredbaboo.LottoMachine.dto;

public class ErrorDTO {
    public final String message;
    public final String ex;

    public ErrorDTO(String message, Exception ex) {
        this.message = message;
        this.ex = ex.getLocalizedMessage();
    }
}
