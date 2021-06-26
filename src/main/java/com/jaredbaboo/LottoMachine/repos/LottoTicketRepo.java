package com.jaredbaboo.LottoMachine.repos;

import com.jaredbaboo.LottoMachine.models.LottoTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LottoTicketRepo extends JpaRepository<LottoTicket, UUID> {

    List findAllByCanceledFalse();
    Page findAllByCanceledFalse(Pageable page);

}
