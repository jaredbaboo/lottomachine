package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.enums.LottoTicketTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LottoSinglePurchaseTicketRequestDTOTest {

    @Test
    void isValid() {
        LottoSinglePurchaseTicketRequestDTO lottoSinglePurchaseTicketRequestDTO = new LottoSinglePurchaseTicketRequestDTO();

        assertFalse(lottoSinglePurchaseTicketRequestDTO.isValid());

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(100));

        assertFalse(lottoSinglePurchaseTicketRequestDTO.isValid());

        lottoSinglePurchaseTicketRequestDTO.setType(LottoTicketTypeEnum.SINGLE_LOTTO);

        assertFalse(lottoSinglePurchaseTicketRequestDTO.isValid());

        lottoSinglePurchaseTicketRequestDTO.setLotteryNumbersDTO(new ArrayList<>());

        assertFalse(lottoSinglePurchaseTicketRequestDTO.isValid());

        lottoSinglePurchaseTicketRequestDTO.getLotteryNumbersDTO().add(new LotteryNumberDTO());

        assertFalse(lottoSinglePurchaseTicketRequestDTO.isValid());

        for(int i=1;i<7; i++){
            lottoSinglePurchaseTicketRequestDTO.getLotteryNumbersDTO().get(0).addNumberToSelection(i);
        }

        assertTrue(lottoSinglePurchaseTicketRequestDTO.isValid());
    }
}