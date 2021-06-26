package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.enums.LottoTicketTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class LottoSinglePurchaseTicketRequestDTO {
    @Getter
    @Setter
    private BigDecimal cashTendered;
    @Getter
    @Setter
    private List<LotteryNumberDTO> lotteryNumbersDTO;
    @Getter
    @Setter
    private LottoTicketTypeEnum type;


    public boolean isValid() {

        if (type == null) {
            return false;
        }

        switch (type) {
            case QUICK_FIVE:
                if (lotteryNumbersDTO == null || lotteryNumbersDTO.size() != 5) {
                    return false;
                }
                break;
            case SINGLE_LOTTO:
                if (lotteryNumbersDTO == null || lotteryNumbersDTO.size() != 1) {
                    return false;
                }
                break;
            case RANDOM_FIVE:
            case RANDOM_LOTTO:
                if (lotteryNumbersDTO != null && lotteryNumbersDTO.size() != 0) {
                    return false;
                }
                break;
        }

        if (lotteryNumbersDTO != null && lotteryNumbersDTO.stream().filter(lotteryNumberDTO -> !lotteryNumberDTO.isValid()).count() > 0) {
            return false;
        }

        return true;
    }


}
