package com.jaredbaboo.LottoMachine.dto;

import com.jaredbaboo.LottoMachine.models.LotteryNumbers;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class LotteryNumberDTO {
    @Getter
    @Setter
    private Set<Integer> lottoSelection;

    public LotteryNumberDTO(LotteryNumbers lotteryNumbers) {
        this.lottoSelection = lotteryNumbers.getLottoSelection();
    }

    public void addNumberToSelection(int selection) {
        if (selection > 0 && selection <= 50) {
            if (lottoSelection == null) {
                lottoSelection = new HashSet<>();
            }

            if (lottoSelection.size() < 6) {
                lottoSelection.add(selection);
            }
        }
    }

    public boolean isValid() {
        if (lottoSelection == null || lottoSelection.size() != 6) {
            return false;
        }
        long count = lottoSelection.stream().filter(integer -> integer <= 50 && integer > 0).count();
        if (count != 6) {
            return false;
        }
        return true;
    }
}
