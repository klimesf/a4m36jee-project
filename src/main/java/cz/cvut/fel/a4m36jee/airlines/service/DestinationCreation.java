package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.event.DestinationCreated;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * @author klimefi1
 */
public class DestinationCreation {

    @Inject
    private Logger logger;

    @Inject
    private DestinationDAO destinationDAO;

    @Inject
    private Event<DestinationCreated> destinationEvent;

    public void create(Destination destination) {
        destinationDAO.save(destination);
        logger.info("Created a new Destination with id: " + destination.getId());
        destinationEvent.fire(new DestinationCreated(destination));
    }

}
