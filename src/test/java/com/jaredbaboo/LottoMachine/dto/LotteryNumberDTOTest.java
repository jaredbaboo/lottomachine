package com.jaredbaboo.LottoMachine.dto;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LotteryNumberDTOTest {


    @Test
    public void addNumberToSelectionTest(){
        LotteryNumberDTO lotteryNumberDTO = new LotteryNumberDTO();
        for (int i=0; i< 10 ; i ++) {
            lotteryNumberDTO.addNumberToSelection(i);
        }
        Assert.assertTrue(lotteryNumberDTO.getLottoSelection().size() == 6);
        lotteryNumberDTO = new LotteryNumberDTO();
        for (int i=0; i< 10 ; i ++) {
            lotteryNumberDTO.addNumberToSelection(1);
        }
        Assert.assertEquals(1, lotteryNumberDTO.getLottoSelection().size());
        lotteryNumberDTO = new LotteryNumberDTO();
        for (int i=0; i< 10 ; i ++) {
            lotteryNumberDTO.addNumberToSelection(i+ 50);
        }
        Assert.assertEquals(1, lotteryNumberDTO.getLottoSelection().size());
    }

    @Test
    public void isValid() {
        LotteryNumberDTO lotteryNumberDTO = new LotteryNumberDTO();
        Assert.assertFalse(lotteryNumberDTO.isValid());

        for (int i=0; i< 3 ; i ++) {
            lotteryNumberDTO.addNumberToSelection(i);
        }
        Assert.assertFalse(lotteryNumberDTO.isValid());

        for (int i=0; i< 6 ; i ++) {
            lotteryNumberDTO.addNumberToSelection(1);
        }
        Assert.assertFalse(lotteryNumberDTO.isValid());
    }
}