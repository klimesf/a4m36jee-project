package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * @author klimefi1
 */
@Path("/flights")
@RequestScoped
public class FlightResource extends GenericResource<Flight> {

    @Inject
    private FlightDAO flightDAO;

    @PostConstruct
    public void init() {
        setDao(flightDAO);
    }

}
