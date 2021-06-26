package com.jaredbaboo.LottoMachine.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class LottoTicket {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter
    private UUID id;
    @Getter
    private LocalDateTime purchaseDate;
    @Getter
    private LocalDateTime validtillDate;
    @Getter @Setter
    @OneToMany
    private List<LotteryNumbers> lottoNumbers;
    @Getter @Setter
    private BigDecimal purchaseAmount;
    @Getter @Setter
    private boolean canceled;


    public LottoTicket() {
        this.purchaseDate = LocalDateTime.now();
        // Naive implementation expect more complicated rules to actually be in place in real world scenario
        this.validtillDate = this.purchaseDate.plusDays(7);
        this.lottoNumbers = new ArrayList<>();
    }

    public LottoTicket(LotteryNumbers lottoNumbers) {
        this();
        this.lottoNumbers = new ArrayList<>();
        this.lottoNumbers.add(lottoNumbers);
    }
    public LottoTicket(List<LotteryNumbers> lottoNumbers) {
        this();
        this.lottoNumbers = lottoNumbers;
    }
}
