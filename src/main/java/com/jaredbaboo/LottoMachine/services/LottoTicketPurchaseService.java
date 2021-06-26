package com.jaredbaboo.LottoMachine.services;

import com.jaredbaboo.LottoMachine.dto.CancelPurchaseLottoResultDTO;
import com.jaredbaboo.LottoMachine.dto.LottoSinglePurchaseTicketRequestDTO;
import com.jaredbaboo.LottoMachine.dto.PurchaseLottoResultDTO;
import com.jaredbaboo.LottoMachine.enums.CurrencyType;
import com.jaredbaboo.LottoMachine.enums.LottoTicketTypeEnum;
import com.jaredbaboo.LottoMachine.exceptions.InvalidAmountTenderedException;
import com.jaredbaboo.LottoMachine.exceptions.InvalidLottoNumbersSubmittedException;
import com.jaredbaboo.LottoMachine.exceptions.InvalidTicketException;
import com.jaredbaboo.LottoMachine.models.LottoAccount;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import com.jaredbaboo.LottoMachine.models.LottoTicketType;
import com.jaredbaboo.LottoMachine.repos.LottoTicketTypeRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Service
public class LottoTicketPurchaseService {
    @Getter
    private final ILottoMachine lottoMachine;
    private final LottoTicketTypeRepo lottoTicketTypeRepo;

    @PostConstruct
    public void init(){

            lottoTicketTypeRepo.save(new LottoTicketType(LottoTicketTypeEnum.SINGLE_LOTTO.getId(),BigDecimal.valueOf(5),1));
            lottoTicketTypeRepo.save(new LottoTicketType(LottoTicketTypeEnum.RANDOM_LOTTO.getId(),BigDecimal.valueOf(5),1));
            lottoTicketTypeRepo.save(new LottoTicketType(LottoTicketTypeEnum.QUICK_FIVE.getId(),BigDecimal.valueOf(25),1));
            lottoTicketTypeRepo.save(new LottoTicketType(LottoTicketTypeEnum.RANDOM_FIVE.getId(),BigDecimal.valueOf(25),1));

    }

    @Autowired
    public LottoTicketPurchaseService(ILottoMachine lottoMachine, LottoTicketTypeRepo lottoTicketTypeRepo) {
        this.lottoMachine = lottoMachine;
        this.lottoTicketTypeRepo = lottoTicketTypeRepo;
    }

    public PurchaseLottoResultDTO purchaseSingleLottoTicket(LottoSinglePurchaseTicketRequestDTO lottoPurchaseTicketRequest) throws InvalidAmountTenderedException, InvalidLottoNumbersSubmittedException {
        if (!lottoPurchaseTicketRequest.isValid() || !lottoPurchaseTicketRequest.getType().equals(LottoTicketTypeEnum.SINGLE_LOTTO)) {
            throw new InvalidLottoNumbersSubmittedException("invalid request received");
        }
        LottoTicketType lottoTicketType = getTicketTypeDetail(LottoTicketTypeEnum.SINGLE_LOTTO);

        if(!isValidTender(lottoPurchaseTicketRequest, lottoTicketType)){
            throw new InvalidAmountTenderedException("Invalid amount tendered");
        }

        BigDecimal requiredChange = lottoPurchaseTicketRequest.getCashTendered().subtract(lottoTicketType.getCost());

        LottoTicket lottoTicket = lottoMachine.placeSingleLottoBet(lottoPurchaseTicketRequest.getLotteryNumbersDTO().get(0), lottoTicketType.getCost());
        LottoAccount lottoAccount = lottoMachine.addFunds(lottoTicketType.getCost());

        Map<CurrencyType, Integer> recommendedDisbursmentMap = new HashMap<>();

        recommendDisbursement(requiredChange, recommendedDisbursmentMap);

        return new PurchaseLottoResultDTO(lottoTicket, recommendedDisbursmentMap);

    }

    public PurchaseLottoResultDTO purchaseRandomLottoTicket(LottoSinglePurchaseTicketRequestDTO lottoPurchaseTicketRequest) throws InvalidLottoNumbersSubmittedException, InvalidAmountTenderedException {
        if (!lottoPurchaseTicketRequest.isValid() || !lottoPurchaseTicketRequest.getType().equals(LottoTicketTypeEnum.RANDOM_LOTTO)) {
            throw new InvalidLottoNumbersSubmittedException("invalid request received");
        }
        LottoTicketType lottoTicketType = getTicketTypeDetail(LottoTicketTypeEnum.RANDOM_LOTTO);
        if(!isValidTender(lottoPurchaseTicketRequest, lottoTicketType)){
            throw new InvalidAmountTenderedException("Invalid amount tendered");
        }

        BigDecimal requiredChange = lottoPurchaseTicketRequest.getCashTendered().subtract(lottoTicketType.getCost());

        LottoTicket lottoTicket = lottoMachine.placeRandomLottoBet(lottoTicketType.getCost());
        LottoAccount lottoAccount = lottoMachine.addFunds(lottoTicketType.getCost());

        Map<CurrencyType, Integer> recommendedDisbursmentMap = new HashMap<>();

        recommendDisbursement(requiredChange, recommendedDisbursmentMap);

        return new PurchaseLottoResultDTO(lottoTicket, recommendedDisbursmentMap);

    }

    public PurchaseLottoResultDTO purchaseQuickFiveLottoTicket(LottoSinglePurchaseTicketRequestDTO lottoPurchaseTicketRequest) throws InvalidAmountTenderedException, InvalidLottoNumbersSubmittedException {
        if (!lottoPurchaseTicketRequest.isValid() || !lottoPurchaseTicketRequest.getType().equals(LottoTicketTypeEnum.QUICK_FIVE)) {
            throw new InvalidLottoNumbersSubmittedException("invalid request received");
        }
        LottoTicketType lottoTicketType = getTicketTypeDetail(LottoTicketTypeEnum.QUICK_FIVE);
        if(!isValidTender(lottoPurchaseTicketRequest, lottoTicketType)){
            throw new InvalidAmountTenderedException("Invalid amount tendered");
        }

        BigDecimal requiredChange = lottoPurchaseTicketRequest.getCashTendered().subtract(lottoTicketType.getCost());

        LottoTicket lottoTicket = lottoMachine.placeQuickFiveBet(lottoPurchaseTicketRequest.getLotteryNumbersDTO(), lottoTicketType.getCost());
        LottoAccount lottoAccount = lottoMachine.addFunds(lottoTicketType.getCost());

        Map<CurrencyType, Integer> recommendedDisbursmentMap = new HashMap<>();

        recommendDisbursement(requiredChange, recommendedDisbursmentMap);

        return new PurchaseLottoResultDTO(lottoTicket, recommendedDisbursmentMap);

    }

    public PurchaseLottoResultDTO purchaseRandomFiveLottoTicket(LottoSinglePurchaseTicketRequestDTO lottoPurchaseTicketRequest) throws InvalidLottoNumbersSubmittedException, InvalidAmountTenderedException {
        if (!lottoPurchaseTicketRequest.isValid() || !lottoPurchaseTicketRequest.getType().equals(LottoTicketTypeEnum.RANDOM_FIVE)) {
            throw new InvalidLottoNumbersSubmittedException("invalid request received");
        }
        LottoTicketType lottoTicketType = getTicketTypeDetail(LottoTicketTypeEnum.RANDOM_FIVE);
        if(!isValidTender(lottoPurchaseTicketRequest, lottoTicketType)){
            throw new InvalidAmountTenderedException("Invalid amount tendered");
        }

        BigDecimal requiredChange = lottoPurchaseTicketRequest.getCashTendered().subtract(lottoTicketType.getCost());

        LottoTicket lottoTicket = lottoMachine.placeRandomFiveBet(lottoTicketType.getCost());
        LottoAccount lottoAccount = lottoMachine.addFunds(lottoTicketType.getCost());

        Map<CurrencyType, Integer> recommendedDisbursmentMap = new HashMap<>();

        recommendDisbursement(requiredChange, recommendedDisbursmentMap);

        return new PurchaseLottoResultDTO(lottoTicket, recommendedDisbursmentMap);

    }


    public CancelPurchaseLottoResultDTO cancelTicket(UUID ticketNumber) throws InvalidTicketException, InvalidAmountTenderedException {
        if (ticketNumber == null) {
            throw new InvalidTicketException("Invalid input");
        }
        LottoTicket lottoTicket = lottoMachine.cancelTicket(ticketNumber);
        if(lottoTicket == null){
            throw new InvalidTicketException("Invalid input");
        }
        BigDecimal balanceBefore = lottoMachine.getBalance();
        if(balanceBefore.subtract(lottoTicket.getPurchaseAmount()).intValue() >0) {
            lottoMachine.withdrawFunds(lottoTicket.getPurchaseAmount());
        }
        else{
            throw new InvalidAmountTenderedException("Error error cancelling ticket");
        }
        BigDecimal balanceAfter = lottoMachine.getBalance();

        if (balanceBefore.compareTo(balanceAfter) == 0) {
            throw new InvalidAmountTenderedException("Error updating Balance");
        }

        Map<CurrencyType, Integer> recommendedDisbursmentMap = new HashMap<>();

        recommendDisbursement(lottoTicket.getPurchaseAmount(), recommendedDisbursmentMap);
        return new CancelPurchaseLottoResultDTO(recommendedDisbursmentMap);
    }

    protected LottoTicketType getTicketTypeDetail(LottoTicketTypeEnum singleLotto) {
        Optional optional = lottoTicketTypeRepo.findById(singleLotto.getId());
        LottoTicketType lottoTicketType = null;
        if(optional.isPresent()) {
            lottoTicketType = (LottoTicketType) optional.get();
        }

        return lottoTicketType;
    }

    protected boolean isValidTender(LottoSinglePurchaseTicketRequestDTO lottoPurchaseTicketRequest, LottoTicketType lottoTicketType) {
        if (lottoPurchaseTicketRequest.getCashTendered().compareTo(lottoTicketType.getCost()) < 0) {
            return false;
        }
        return true;
    }

    protected void recommendDisbursement(BigDecimal requiredChange, Map<CurrencyType, Integer> recommendedDisburesment) {
        List<CurrencyType> toSort = new ArrayList<>();
        toSort.addAll(Arrays.asList(CurrencyType.values()));
        toSort.sort((t0, t1) -> t1.getValue() - t0.getValue());

        for (CurrencyType currencyType : toSort) {
            if (requiredChange.intValue() == 0) {
                break;
            }
            int remainder = requiredChange.intValue() % currencyType.getValue();

            int ofTypeToUse = requiredChange.intValue() / currencyType.getValue();


            if(ofTypeToUse > 0){
                recommendedDisburesment.put(currencyType, ofTypeToUse);
                requiredChange = BigDecimal.valueOf(requiredChange.intValue() - currencyType.getValue() * ofTypeToUse);
            }

        }
    }
}
