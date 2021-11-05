package lsd.smartparking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsd.smartparking.model.Ticket;
import lsd.smartparking.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;


    @PreAuthorize("hasAnyAuthority('DRIVER', 'POLICEMAN')")
    @GetMapping("/")
    public ResponseEntity<List<Ticket>> getTickets(Authentication auth) {
        List<Ticket> tickets = ticketService.getTickets(auth.getName());
    	return new ResponseEntity<>(tickets, !tickets.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyAuthority('DRIVER', 'POLICEMAN')")
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<Ticket> buyTicket(@Valid @RequestBody Ticket ticket) {
        ticket = ticketService.buyTicket(ticket);
    	return new ResponseEntity<>(ticket, ticket != null ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }
    
}