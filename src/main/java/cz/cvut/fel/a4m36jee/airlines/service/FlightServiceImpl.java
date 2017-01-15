package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for {@link Flight}.
 *
 * @author moravja8
 */
@Stateless
@Transactional
public class FlightServiceImpl implements FlightService {

    private final Logger logger;

    private final FlightDAO flightDAO;

    private final ReservationService reservationService;

    @Inject
    public FlightServiceImpl(Logger logger, FlightDAO flightDAO, ReservationService reservationService) {
        this.logger = logger;
        this.flightDAO = flightDAO;
        this.reservationService = reservationService;
    }

    @Override
    public List<Flight> list() {
        logger.info("List of all flights requested.");
        List<Flight> flights = flightDAO.list();
        flights.forEach(this::addNumberOfFreeSeats);
        logger.info("Returning " + flights.size() + " flights.");
        return flights;
    }

    @Override
    public List<Flight> listByDestinationId(final Long destinationId) {
        logger.info("List of all flights by outgoing or incoming destination.");
        List<Flight> flights = new ArrayList<>();
        flights.addAll(flightDAO.findBy("from", destinationId));
        flights.addAll(flightDAO.findBy("to", destinationId));
        flights.forEach(this::addNumberOfFreeSeats);
        logger.info("Returning " + flights.size() + " flights.");
        return flights;
    }

    @Override
    public Flight get(final Long id) {
        logger.info("Flight with id " + id + " requested.");
        final Flight flight = flightDAO.find(id);
        logger.info("Flight loaded.");
        addNumberOfFreeSeats(flight);
        logger.info("Returning flight: " + flight);
        return flight;
    }

    private void addNumberOfFreeSeats(final Flight flight) {
        logger.info("Adding number of free seats.");
        int numberOfReservationsOnFlight = reservationService.listByFlightId(flight.getId()).size();
        flight.setFreeSeats(flight.getSeats() - numberOfReservationsOnFlight);
        logger.info("Number of free seats added.");
    }

    @Override
    public void create(final Flight flight) {
        flightDAO.save(flight);
        logger.info("Created a new Flight with id: " + flight.getId());
    }

    @Override
    public void delete(final Long id) {
        delete(flightDAO.find(id));
    }

    @Override
    public void delete(final Flight flight) {
        logger.info("Deleting Flight with id " + flight.getId());
        List<Reservation> reservations = reservationService.listByFlightId(flight.getId());
        reservations.forEach(reservationService::delete);
        flightDAO.delete(flight);
        logger.info("Flight deleted.");
    }

    @Override
    public void update(final Flight flight) {
        logger.info("Updating flight with id " + flight.getId());
        flightDAO.update(flight);
        logger.info("Flight updated");
    }
}
