package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for {@link Flight}.
 *
 * @author moravja8
 */
@Stateless
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
        logger.info("Deleting Flight with id " + id);
        flightDAO.delete(id);
        logger.info("Flight deleted.");
    }

    @Override
    public void update(final Flight flight) {
        logger.info("Updating flight with id " + flight.getId());
        flightDAO.update(flight);
        logger.info("Flight updated");
    }


}
