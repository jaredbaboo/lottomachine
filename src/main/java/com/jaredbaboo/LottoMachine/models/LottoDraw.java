package com.jaredbaboo.LottoMachine.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class LottoDraw {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter
    private UUID id;
    @Getter
    private LocalDateTime drawDate;
    @OneToOne
    @Getter
    @Setter
    private LotteryNumbers lottoNumbers;
}
