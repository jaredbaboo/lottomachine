package com.jaredbaboo.LottoMachine.models;

import com.jaredbaboo.LottoMachine.dto.LotteryNumberDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class LotteryNumbers {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter
    private UUID id;
    @Getter @Setter
    @ElementCollection
    private Set<Integer> lottoSelection;

    public LotteryNumbers(LotteryNumberDTO lotteryNumber) {
        lottoSelection = lotteryNumber.getLottoSelection();
    }
}
