package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for {@link Destination}.
 *
 * @author klimefi1, moravja8
 */
@Stateless
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final Logger logger;

    private final DestinationDAO destinationDAO;

    private final FlightService flightService;

    @Inject
    public DestinationServiceImpl(Logger logger, DestinationDAO destinationDAO, FlightService flightService) {
        this.logger = logger;
        this.destinationDAO = destinationDAO;
        this.flightService = flightService;
    }

    @Override
    public List<Destination> list() {
        logger.info("List of all destinations requested.");
        List<Destination> destinations = destinationDAO.list();
        logger.info("Returning " + destinations.size() + " destinations.");
        return destinations;
    }

    @Override
    public Destination get(final Long id) {
        logger.info("Destination with id " + id + " requested.");
        final Destination destination = destinationDAO.find(id);
        logger.info("Returning destination: " + destination);
        return destination;
    }

    @Override
    public void create(final Destination destination) {
        destinationDAO.save(destination);
        logger.info("Created a new Destination with id: " + destination.getId());
    }

    @Override
    public void delete(final Long id) {
        delete(get(id));
    }

    @Override
    public void delete(final Destination destination) {
        logger.info("Deleting Destination with id " + destination.getId());
        List<Flight> flights = flightService.listByDestinationId(destination.getId());
        flights.forEach(flightService::delete);
        destinationDAO.delete(destination);
        logger.info("Destination deleted.");
    }

    @Override
    public void update(final Destination destination) {
        logger.info("Updating destination with id " + destination.getId());
        destinationDAO.update(destination);
        logger.info("Destination updated");
    }

    @Override
    public List<Destination> findByName(String name) {
        logger.info("Finding Destination by name " + name);
        return destinationDAO.findBy("name", name);
    }
}
