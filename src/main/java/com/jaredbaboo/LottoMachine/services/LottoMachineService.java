package com.jaredbaboo.LottoMachine.services;

import com.jaredbaboo.LottoMachine.dto.LotteryNumberDTO;
import com.jaredbaboo.LottoMachine.exceptions.InvalidAmountTenderedException;
import com.jaredbaboo.LottoMachine.exceptions.InvalidLottoNumbersSubmittedException;
import com.jaredbaboo.LottoMachine.models.LottoAccount;
import com.jaredbaboo.LottoMachine.models.LotteryNumbers;
import com.jaredbaboo.LottoMachine.models.LottoDraw;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import com.jaredbaboo.LottoMachine.repos.LotteryNumbersRepo;
import com.jaredbaboo.LottoMachine.repos.LottoAccountRepo;
import com.jaredbaboo.LottoMachine.repos.LottoDrawRepo;
import com.jaredbaboo.LottoMachine.repos.LottoTicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LottoMachineService implements ILottoMachine {

    private final LottoAccountRepo lottoAccountRepo;
    private final LotteryNumbersRepo lotteryNumbersRepo;
    private final LottoTicketRepo lottoTicketRepo;
    private final LottoDrawRepo lottoDrawRepo;

    @PostConstruct
    public void init(){
        if(lottoAccountRepo.findFirstByOpenTrue() == null){
            lottoAccountRepo.save(new LottoAccount(BigDecimal.ZERO));
        }
    }

    @Autowired
    public LottoMachineService(LottoAccountRepo lottoAccountRepo, LotteryNumbersRepo lotteryNumbersRepo, LottoTicketRepo lottoTicketRepo,
                               LottoDrawRepo lottoDrawRepo) {
        this.lottoAccountRepo = lottoAccountRepo;
        this.lotteryNumbersRepo = lotteryNumbersRepo;
        this.lottoTicketRepo = lottoTicketRepo;
        this.lottoDrawRepo = lottoDrawRepo;
    }

    @Override
    public LottoAccount addFunds(BigDecimal purchaseAmount) {

        LottoAccount lottoAccount = lottoAccountRepo.findFirstByOpenTrue();

        lottoAccount.setBalance(lottoAccount.getBalance().add(purchaseAmount));

        lottoAccount = lottoAccountRepo.save(lottoAccount);

        return lottoAccount;

    }


    @Override
    public LottoTicket placeSingleLottoBet(LotteryNumberDTO lotteryNumber, BigDecimal purchaseAmount) throws InvalidLottoNumbersSubmittedException {
        if (!lotteryNumber.isValid()) {
            throw new InvalidLottoNumbersSubmittedException("invalid Lotto numbers sent");
        }
        LottoTicket lottoTicket = new LottoTicket(new LotteryNumbers(lotteryNumber));
        lottoTicket.setPurchaseAmount(purchaseAmount);
        lotteryNumbersRepo.save(lottoTicket.getLottoNumbers().get(0));
        lottoTicket =lottoTicketRepo.save(lottoTicket);

        return lottoTicket;

    }

    @Override
    public LottoTicket placeRandomLottoBet(BigDecimal purchaseAmount) {
        LottoTicket lottoTicket = new LottoTicket(new LotteryNumbers(generateLotteryNumbers()));
        lotteryNumbersRepo.save(lottoTicket.getLottoNumbers().get(0));
        lottoTicket.setPurchaseAmount(purchaseAmount);
        return lottoTicketRepo.save(lottoTicket);
    }

    @Override
    public LottoTicket placeQuickFiveBet(List<LotteryNumberDTO> lotteryNumbers, BigDecimal purchaseAmount) throws InvalidLottoNumbersSubmittedException {
        List<LotteryNumberDTO> invalidNumbers = lotteryNumbers
                .stream()
                .filter(lotteryNumberDTO -> !lotteryNumberDTO.isValid()).collect(Collectors.toList());
        if (invalidNumbers.size() > 0) {
            throw new InvalidLottoNumbersSubmittedException("invalid Lotto numbers sent");
        }
        LottoTicket lottoTicket = new LottoTicket();
        lottoTicket.setPurchaseAmount(purchaseAmount);
        List<LotteryNumbers> playedLottoNumbers = lottoTicket.getLottoNumbers();
        lotteryNumbers.forEach(lotteryNumberDTO -> {
            LotteryNumbers lotteryNumbers1 = new LotteryNumbers(lotteryNumberDTO);
            playedLottoNumbers.add(lotteryNumbers1);
            lotteryNumbersRepo.save(lotteryNumbers1);
        });

        return lottoTicketRepo.save(lottoTicket);
    }

    @Override
    public LottoTicket placeRandomFiveBet(BigDecimal purchaseAmount) {
        LottoTicket lottoTicket = new LottoTicket(new LotteryNumbers(generateLotteryNumbers()));
        lottoTicket.setPurchaseAmount(purchaseAmount);
        List<LotteryNumbers> playedlottoNumbers = lottoTicket.getLottoNumbers();
        for (int i = 0; i < 4; i++) {
            playedlottoNumbers.add(new LotteryNumbers(generateLotteryNumbers()));
        }
        playedlottoNumbers.forEach(lotteryNumbers -> lotteryNumbersRepo.save(lotteryNumbers));


        return lottoTicketRepo.save(lottoTicket);
    }

    protected LotteryNumberDTO generateLotteryNumbers() {
        LotteryNumberDTO lotteryNumberDTO = new LotteryNumberDTO();
        Random rd = new Random();
        while (!lotteryNumberDTO.isValid()) {
            lotteryNumberDTO.addNumberToSelection(rd.nextInt(51));
        }
        return lotteryNumberDTO;
    }

    @Override
    public LottoTicket cancelTicket(UUID id) {
        Optional<LottoTicket> lottoTicket = lottoTicketRepo.findById(id);
        if (lottoTicket.isPresent()) {
            LottoTicket ticket = lottoTicket.get();
            ticket.setCanceled(true);
            ticket = lottoTicketRepo.save(ticket);
            return ticket;
        }

        return null;
    }

    @Override
    public LottoTicket printTicket(UUID id) {
        Optional<LottoTicket> lottoTicket = lottoTicketRepo.findById(id);
        return lottoTicket.orElse(null);

    }

    @Override
    public void resultTicket(LottoTicket lottoticket) {
        /**
         * implementing a naive ticket check against the lotto draw results between purchase and when its valid
         */
        LottoDraw lottoDraw = lottoDrawRepo.findFirstByDrawDateBetween(lottoticket.getPurchaseDate(), lottoticket.getValidtillDate());
        Set<Integer> matchedValues = new HashSet<>();
        lottoticket.getLottoNumbers().forEach(lotteryNumbers -> {
            Set<Integer> sortedSelection = lotteryNumbers.getLottoSelection().stream().sorted().collect(Collectors.toSet());
            Set<Integer> sortedDrawResults = lottoDraw.getLottoNumbers().getLottoSelection().stream().sorted().collect(Collectors.toSet());

            for (Integer selectedValue : sortedSelection) {
                for (Integer drawValue : sortedDrawResults) {
                    if (selectedValue.equals(drawValue)) {
                        matchedValues.add(selectedValue);
                    }
                }
            }
        });
        if (matchedValues.size() < 3) {
            // No Winners
        } else {
            //winners
        }

    }

    @Override
    public BigDecimal getBalance() {
        LottoAccount lottoAccount = lottoAccountRepo.findFirstByOpenTrue();
        return lottoAccount.getBalance();
    }

    @Override
    public Page getTickets(PageRequest pageRequest) {
        return lottoTicketRepo.findAllByCanceledFalse(pageRequest);
    }

    @Override
    public LottoAccount withdrawFunds(BigDecimal amount) throws InvalidAmountTenderedException {
        LottoAccount lottoAccount = lottoAccountRepo.findFirstByOpenTrue();
        if (lottoAccount.getBalance().compareTo(amount) < 0) {
            throw new InvalidAmountTenderedException("insufficient funds");
        }

        lottoAccount.setBalance(lottoAccount.getBalance().subtract(amount));

        return lottoAccountRepo.save(lottoAccount);
    }
}
