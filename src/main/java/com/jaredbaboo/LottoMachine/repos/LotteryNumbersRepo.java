package com.jaredbaboo.LottoMachine.repos;

import com.jaredbaboo.LottoMachine.models.LotteryNumbers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LotteryNumbersRepo  extends JpaRepository<LotteryNumbers, UUID> {
}
