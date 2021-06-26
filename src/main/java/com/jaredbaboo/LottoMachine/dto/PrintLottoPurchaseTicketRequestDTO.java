package com.jaredbaboo.LottoMachine.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class PrintLottoPurchaseTicketRequestDTO {
    @Getter
    @Setter
    private UUID ticketNumber;
}
