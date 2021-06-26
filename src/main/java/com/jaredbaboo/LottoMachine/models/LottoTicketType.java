package com.jaredbaboo.LottoMachine.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class LottoTicketType {
    @Id
    private long id;
    @Getter
    @Setter
    private BigDecimal cost;
    @Getter
    @Setter
    private int lottoGames;

    public LottoTicketType(long id, BigDecimal cost, int games) {
        this.id=id;
        this.cost = cost;
        this.lottoGames=games;
    }
}
