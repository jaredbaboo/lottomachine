package com.jaredbaboo.LottoMachine.enums;

import lombok.Getter;

public enum CurrencyType {
    R1(1),R2(2),R5(5),R10(10),R50(50), R100(100);

    @Getter
    private int value;
    CurrencyType(int value) {
        this.value = value;
    }


}
