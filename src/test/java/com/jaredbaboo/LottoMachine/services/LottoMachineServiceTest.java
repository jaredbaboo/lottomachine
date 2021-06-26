package com.jaredbaboo.LottoMachine.services;

import com.jaredbaboo.LottoMachine.dto.LotteryNumberDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LottoMachineServiceTest {
    @Autowired
    private LottoMachineService lottoMachineService;
    @Test
    void generateLotteryNumbers() {

        LotteryNumberDTO lotteryNumberDTO = lottoMachineService.generateLotteryNumbers();

        Assert.assertTrue(lotteryNumberDTO.isValid());

        LotteryNumberDTO lotteryNumberDTO2 = lottoMachineService.generateLotteryNumbers();

        Assert.assertTrue(lotteryNumberDTO2.isValid());

        LotteryNumberDTO lotteryNumberDTO3 = lottoMachineService.generateLotteryNumbers();

        Assert.assertTrue(lotteryNumberDTO3.isValid());

        Assert.assertNotEquals(lotteryNumberDTO,lotteryNumberDTO2);
        Assert.assertNotEquals(lotteryNumberDTO,lotteryNumberDTO3);
        Assert.assertNotEquals(lotteryNumberDTO2,lotteryNumberDTO3);
    }


}