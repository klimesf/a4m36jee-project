package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * @author klimefi1
 */
@Path("/destinations")
@RequestScoped
public class DestinationResource extends GenericResource<Destination> {

    @Inject
    private DestinationDAO destinationDAO;

    @PostConstruct
    public void init() {
        setDao(destinationDAO);
    }

}