package com.jaredbaboo.LottoMachine.repos;

import com.jaredbaboo.LottoMachine.models.LottoDraw;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LottoDrawRepo extends JpaRepository<LottoDraw, UUID> {
    LottoDraw findFirstByDrawDateBetween(LocalDateTime purchaseDate,LocalDateTime validtillDate );
}
