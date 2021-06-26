package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.enums.CurrencyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
public class CancelPurchaseLottoResultDTO {
    @Getter @Setter
    private Map<CurrencyType, Integer> recommendedDisbursmentMap;
    public CancelPurchaseLottoResultDTO(Map<CurrencyType, Integer> recommendedDisbursmentMap) {
        this.recommendedDisbursmentMap=recommendedDisbursmentMap;
    }
}
