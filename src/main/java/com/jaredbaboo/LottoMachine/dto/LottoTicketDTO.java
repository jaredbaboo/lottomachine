package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.models.LotteryNumbers;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class LottoTicketDTO {
    @Getter
    @Setter
    private UUID ticketNumber;
    @Getter
    @Setter
    private LocalDateTime purchaseDate;
    @Getter
    @Setter
    private LocalDateTime validtillDate;
    @Getter
    @Setter
    private List<LotteryNumbers> lottoNumbers;

    public LottoTicketDTO(LottoTicket lottoTicket) {
        this.ticketNumber =lottoTicket.getId();
        this.purchaseDate = lottoTicket.getPurchaseDate();
        this.validtillDate = lottoTicket.getValidtillDate();
        this.lottoNumbers = lottoTicket.getLottoNumbers();
    }
}
