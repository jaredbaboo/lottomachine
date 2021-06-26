package com.jaredbaboo.LottoMachine.repos;

import com.jaredbaboo.LottoMachine.models.LottoTicket;
import com.jaredbaboo.LottoMachine.models.LottoTicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LottoTicketTypeRepo extends JpaRepository<LottoTicketType, Long> {


}
