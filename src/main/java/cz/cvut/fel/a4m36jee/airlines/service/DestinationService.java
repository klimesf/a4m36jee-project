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

    public List<Destination> list() {
        return destinationDAO.list();
    }

    public Destination get(final long id) {
        Destination destination = destinationDAO.find(id);
        if (destination == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return destination;
    }

    public void create(final Destination destination) {
        destinationDAO.save(destination);
    }

    public void delete(final long id) {
        destinationDAO.delete(id);
    }

}
