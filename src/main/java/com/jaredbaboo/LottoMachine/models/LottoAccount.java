package com.jaredbaboo.LottoMachine.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class LottoAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    private BigDecimal balance;
    private boolean open;
    public LottoAccount(){
        this.balance = BigDecimal.ZERO;
        this.open = true;
    }
    public LottoAccount(BigDecimal balance) {
        this();
        this.balance = balance;
    }
}
