package com.jaredbaboo.LottoMachine.services;

import com.jaredbaboo.LottoMachine.dto.LottoSinglePurchaseTicketRequestDTO;
import com.jaredbaboo.LottoMachine.enums.CurrencyType;
import com.jaredbaboo.LottoMachine.enums.LottoTicketTypeEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LottoTicketPurchaseServiceTest {
    @Autowired
    private LottoTicketPurchaseService lottoTicketPurchaseService;

    @Test
    void isValidTender() {
        LottoSinglePurchaseTicketRequestDTO lottoSinglePurchaseTicketRequestDTO = new LottoSinglePurchaseTicketRequestDTO();
        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(25));
        Assert.assertTrue(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.SINGLE_LOTTO)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(4));
        Assert.assertFalse(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.SINGLE_LOTTO)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(25));
        Assert.assertTrue(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.RANDOM_LOTTO)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(4));
        Assert.assertFalse(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.RANDOM_LOTTO)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(25));
        Assert.assertTrue(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.QUICK_FIVE)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(4));
        Assert.assertFalse(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.QUICK_FIVE)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(25));
        Assert.assertTrue(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.RANDOM_FIVE)));

        lottoSinglePurchaseTicketRequestDTO.setCashTendered(BigDecimal.valueOf(4));
        Assert.assertFalse(lottoTicketPurchaseService.isValidTender(lottoSinglePurchaseTicketRequestDTO,
                lottoTicketPurchaseService.getTicketTypeDetail(LottoTicketTypeEnum.RANDOM_FIVE)));
    }

    @Test
    void recommendDisbursement() {
        Map<CurrencyType, Integer> recommendedDisburesment = new HashMap<CurrencyType, Integer>();
        lottoTicketPurchaseService.recommendDisbursement(BigDecimal.valueOf(75),recommendedDisburesment);

        Assert.assertEquals(3, recommendedDisburesment.size());
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R50),0);
        Assert.assertEquals(2, recommendedDisburesment.get(CurrencyType.R10),0);
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R5),0);


        recommendedDisburesment = new HashMap<CurrencyType, Integer>();
        lottoTicketPurchaseService.recommendDisbursement(BigDecimal.valueOf(92),recommendedDisburesment);

        Assert.assertEquals(3, recommendedDisburesment.size());
        //Assert.assertEquals(0, recommendedDisburesment.get(CurrencyType.R100),0);
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R50),0);
        Assert.assertEquals(4, recommendedDisburesment.get(CurrencyType.R10),0);
//        Assert.assertEquals(0, recommendedDisburesment.get(CurrencyType.R5),0);
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R2),0);
//        Assert.assertEquals(0, recommendedDisburesment.get(CurrencyType.R1),0);

        recommendedDisburesment = new HashMap<CurrencyType, Integer>();
        lottoTicketPurchaseService.recommendDisbursement(BigDecimal.valueOf(13),recommendedDisburesment);

        Assert.assertEquals(3, recommendedDisburesment.size());
        //Assert.assertEquals(0, recommendedDisburesment.get(CurrencyType.R100),0);
//        Assert.assertEquals(0, recommendedDisburesment.get(CurrencyType.R50),0);
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R10),0);
//        Assert.assertEquals(0, recommendedDisburesment.get(CurrencyType.R5),0);
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R2),0);
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R1),0);

        recommendedDisburesment = new HashMap<CurrencyType, Integer>();
        lottoTicketPurchaseService.recommendDisbursement(BigDecimal.valueOf(10),recommendedDisburesment);

        Assert.assertEquals(1, recommendedDisburesment.size());
        Assert.assertNull(recommendedDisburesment.get(CurrencyType.R100));
        Assert.assertNull(recommendedDisburesment.get(CurrencyType.R50));
        Assert.assertEquals(1, recommendedDisburesment.get(CurrencyType.R10),0);
        Assert.assertNull( recommendedDisburesment.get(CurrencyType.R5));
        Assert.assertNull(recommendedDisburesment.get(CurrencyType.R2));
        Assert.assertNull( recommendedDisburesment.get(CurrencyType.R1));
    }
}