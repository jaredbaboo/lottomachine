package com.jaredbaboo.LottoMachine.repos;

import com.jaredbaboo.LottoMachine.models.LottoAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface LottoAccountRepo extends JpaRepository<LottoAccount, Long> {

    LottoAccount findFirstByOpenTrue();

}
