package com.jaredbaboo.LottoMachine.enums;

import lombok.Getter;

public enum LottoTicketTypeEnum {
    SINGLE_LOTTO(1),
    RANDOM_LOTTO(2),
    QUICK_FIVE(3),
    RANDOM_FIVE(4);

    @Getter
    private long id;

    LottoTicketTypeEnum(long id) {
        this.id = id;
    }
}
