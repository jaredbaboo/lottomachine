package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.enums.CurrencyType;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class PurchaseLottoResultDTO {

    @Getter @Setter
    private LottoTicketDTO lottoTicketDTO;
    @Getter @Setter
    Map<CurrencyType, Integer> recommendedDisbursement;
    public PurchaseLottoResultDTO(LottoTicket lottoTicket, Map<CurrencyType, Integer> recommendedDisbursement) {
        this.recommendedDisbursement=recommendedDisbursement;
        this.lottoTicketDTO = new LottoTicketDTO(lottoTicket);
    }
}
