package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author slavion3
 */
@Stateless
@Transactional
public class DestinationService {

    @Inject
    DestinationDAO destinationDAO;

    public List<Destination> getAllDestinations() {
        return destinationDAO.list();
    }

    public Destination getDestination(final long id) {
        Destination destination = destinationDAO.find(id);
        if (destination == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return destination;
    }

    public void createDestination(final Destination destination) {
        destinationDAO.save(destination);
    }

    public void updateDestination(final Destination destination) {
        destinationDAO.update(destination);
    }

    public void deleteDestination(final long id) {
        destinationDAO.delete(id);
    }

}
