package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.models.LotteryNumbers;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrintTicketResponseDTO {
    @Getter @Setter
    private UUID TicketNumber;
    @Getter @Setter
    private LocalDateTime purchaseDate;
    @Getter @Setter
    private LocalDateTime validTillDate;
    @OneToMany
    @Getter @Setter
    private List<LotteryNumberDTO> lottoNumbers;
    @Getter @Setter
    private BigDecimal purchaseAmount;
    public PrintTicketResponseDTO(LottoTicket printTicket) {
        this.TicketNumber = printTicket.getId();
        this.purchaseDate = printTicket.getPurchaseDate();
        this.validTillDate = printTicket.getValidtillDate();
        this.purchaseAmount = printTicket.getPurchaseAmount();
        this.lottoNumbers = new ArrayList<>();
        printTicket.getLottoNumbers().forEach(lotteryNumbers -> {
            this.lottoNumbers.add(new LotteryNumberDTO(lotteryNumbers));
        });
    }
}
