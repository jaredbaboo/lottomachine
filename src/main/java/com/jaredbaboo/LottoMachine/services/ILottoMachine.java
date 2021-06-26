package com.jaredbaboo.LottoMachine.services;

import com.jaredbaboo.LottoMachine.dto.LotteryNumberDTO;
import com.jaredbaboo.LottoMachine.exceptions.InvalidAmountTenderedException;
import com.jaredbaboo.LottoMachine.exceptions.InvalidLottoNumbersSubmittedException;
import com.jaredbaboo.LottoMachine.models.LottoAccount;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ILottoMachine {

    LottoAccount addFunds(BigDecimal purchaseAmount);

    LottoTicket placeSingleLottoBet(LotteryNumberDTO lotteryNumber, BigDecimal purchaseAmount) throws InvalidLottoNumbersSubmittedException;

    LottoTicket placeRandomLottoBet(BigDecimal purchaseAmount);

    LottoTicket placeQuickFiveBet(List<LotteryNumberDTO> lotteryNumbers,BigDecimal purchaseAmount) throws InvalidLottoNumbersSubmittedException;

    LottoTicket placeRandomFiveBet(BigDecimal purchaseAmount);

    LottoTicket cancelTicket(UUID id);

    LottoTicket printTicket(UUID id);

    void resultTicket(LottoTicket lottoticket);

    BigDecimal getBalance();

    Page<LottoTicket> getTickets(PageRequest pageRequest);

    LottoAccount withdrawFunds(BigDecimal amount) throws InvalidAmountTenderedException;
}