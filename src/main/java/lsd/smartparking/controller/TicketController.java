package lsd.smartparking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Ticket;
import lsd.smartparking.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;


    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Optional<Ticket>> getTicket(@RequestParam(required = true) String id) {
        Optional<Ticket> ticket = ticketService.getTicket(id);
    	return new ResponseEntity<>(ticket, ticket.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/", params = "payer")
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam(required = true) String owner) {
        List<Ticket> tickets = ticketService.getTickets(owner);
    	return new ResponseEntity<>(tickets, !tickets.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Ticket> buyTicket(@Valid @RequestBody Ticket ticket) {
        ticket = ticketService.buyTicket(ticket);
    	return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
    
}
