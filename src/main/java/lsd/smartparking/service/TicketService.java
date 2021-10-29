package lsd.smartparking.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lsd.smartparking.model.Ticket;
import lsd.smartparking.repository.TicketRepository;
import lsd.smartparking.repository.VehicleRepository;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepo;
    @Autowired
    private VehicleRepository vehicleRepo;
    

    public Optional<Ticket> getTicket(String id) {
        return ticketRepo.findById(id);
    }

    public List<Ticket> getTickets(String payer) {
        return ticketRepo.findByPayer(payer);
    }

    @Transactional
    public Ticket buyTicket(@Valid Ticket ticket) {
        if (!vehicleRepo.existsById(ticket.getVehicle())) return null;
        return ticketRepo.save(ticket);
    }

}