package lsd.smartparking.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lsd.smartparking.enums.TicketType;
import lsd.smartparking.model.Ticket;
import lsd.smartparking.model.Vehicle;
import lsd.smartparking.repository.ParkingRepository;
import lsd.smartparking.repository.ParkingSlotRepository;
import lsd.smartparking.repository.TicketRepository;
import lsd.smartparking.repository.VehicleRepository;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepo;
    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private ParkingRepository parkingRepo;
    @Autowired
    private ParkingSlotRepository slotRepo;
    

    public Optional<Ticket> getTicket(String id) {
        return ticketRepo.findById(id);
    }

    public List<Ticket> getTickets(String payer) {
        return ticketRepo.findByPayer(payer);
    }

    @Transactional
    public Ticket buyTicket(@Valid Ticket ticket) {
        if (!vehicleRepo.existsById(ticket.getVehicle())) return null;
        String parking = ticket.getParking();
        TicketType type = ticket.getType();
        Vehicle vehicle = vehicleRepo.findById(ticket.getVehicle()).get();
        if (ticketRepo.existsByTimeRangeAndVehicle(ticket.getInception(), ticket.getExpiration(), ticket.getVehicle())) return null;
        if (type == TicketType.PARKING) {
            if (!parkingRepo.existsById(parking)) return null;
            if (parkingRepo.findById(parking).get().getSlots().get(vehicle.getType()) == null) return null;
        } else if (type == TicketType.SLOT) {
            if (!slotRepo.existsById(parking)) return null;
            if (slotRepo.findById(parking).get().getType() != vehicle.getType()) return null;
            if (ticketRepo.existsByTimeRangeAndParking(ticket.getInception(), ticket.getExpiration(), parking)) return null;
        } else return null;
        return ticketRepo.save(ticket);
    }

}