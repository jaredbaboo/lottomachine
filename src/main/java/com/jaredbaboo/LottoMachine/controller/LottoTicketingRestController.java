package com.jaredbaboo.LottoMachine.controller;

import com.jaredbaboo.LottoMachine.dto.*;
import com.jaredbaboo.LottoMachine.exceptions.InvalidAmountTenderedException;
import com.jaredbaboo.LottoMachine.exceptions.InvalidLottoNumbersSubmittedException;
import com.jaredbaboo.LottoMachine.exceptions.InvalidTicketException;
import com.jaredbaboo.LottoMachine.models.LottoTicket;
import com.jaredbaboo.LottoMachine.services.LottoMachineService;
import com.jaredbaboo.LottoMachine.services.LottoTicketPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.UUID;


@RestController
@RequestMapping("/lottomachine")
public class LottoTicketingRestController {

    private final LottoTicketPurchaseService lottoTicketPurchaseService;
    private final LottoMachineService lottoMachineService;

    @Autowired
    public LottoTicketingRestController(LottoTicketPurchaseService lottoTicketPurchaseService, LottoMachineService lottoMachineService) {
        this.lottoTicketPurchaseService = lottoTicketPurchaseService;
        this.lottoMachineService = lottoMachineService;
    }
    @PostMapping("/purchase")
    @ResponseBody
    public PurchaseLottoResultDTO buyLottoTickets(@RequestBody LottoSinglePurchaseTicketRequestDTO lottoPurchaseTicketRequest) throws InvalidAmountTenderedException, InvalidLottoNumbersSubmittedException {
        switch( lottoPurchaseTicketRequest.getType()){
            case SINGLE_LOTTO:
                return this.lottoTicketPurchaseService.purchaseSingleLottoTicket(lottoPurchaseTicketRequest);
            case RANDOM_LOTTO:
                return this.lottoTicketPurchaseService.purchaseRandomLottoTicket(lottoPurchaseTicketRequest);
            case QUICK_FIVE:
                return this.lottoTicketPurchaseService.purchaseQuickFiveLottoTicket(lottoPurchaseTicketRequest);
            case RANDOM_FIVE:
                return this.lottoTicketPurchaseService.purchaseRandomFiveLottoTicket(lottoPurchaseTicketRequest);
            default:
                throw new RuntimeException("unknown game type");

        }
    }
    @DeleteMapping("/ticket/{ticketNumber}")
    @ResponseBody
    public CancelPurchaseLottoResultDTO cancelLottoTickets(@PathVariable("ticketNumber") UUID ticketNumber) throws InvalidTicketException, InvalidAmountTenderedException {
        return this.lottoTicketPurchaseService.cancelTicket(ticketNumber);
    }

    @RequestMapping(method = RequestMethod.GET,path = "print")
    @ResponseBody
    public PrintTicketResponseDTO printLottoTickets(@RequestParam(value="ticketNumber") UUID ticketNumber) {
        return new PrintTicketResponseDTO(lottoMachineService.printTicket(ticketNumber));
    }
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<LottoTicket> getLottoTickets(@RequestParam(value = "page",required = false) int page, @RequestParam(value="size", required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "purchaseDate"));
        return lottoMachineService.getTickets(pageRequest);
    }

}
