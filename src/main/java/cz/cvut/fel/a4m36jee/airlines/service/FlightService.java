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
public class FlightService implements CrudService<Flight> {

    @Inject
    private Logger logger;

    @Inject
    private FlightDAO flightDAO;

    @Override
    public List<Flight> list() {
        logger.info("List of all flights requested.");
        List<Flight> flights = flightDAO.list();
        logger.info("Returning " + flights.size() + " flights.");
        return flights;
    }

    @Override
    public Flight get(final Long id) {
        logger.info("Flight with id " + id +  " requested.");
        final Flight flight = flightDAO.find(id);
        logger.info("Returning flight: " + flight);
        return flight;
    }

    @Override
    public void create(final Flight flight) {
        flightDAO.save(flight);
        logger.info("Created a new Flight with id: " + flight.getId());
    }
}
