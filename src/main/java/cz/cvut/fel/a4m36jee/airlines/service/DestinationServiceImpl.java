package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for {@link Destination}.
 * s
 *
 * @author klimefi1, moravja8
 */
@Stateless
public class DestinationServiceImpl implements DestinationService {

    private final Logger logger;

    private final DestinationDAO destinationDAO;

    @Inject
    public DestinationServiceImpl(Logger logger, DestinationDAO destinationDAO) {
        this.logger = logger;
        this.destinationDAO = destinationDAO;
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
    @Transactional
    public void create(final Destination destination) {
        destinationDAO.save(destination);
        logger.info("Created a new Destination with id: " + destination.getId());
    }
}