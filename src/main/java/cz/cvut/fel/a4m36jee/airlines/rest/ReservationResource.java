package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 * @author klimefi1
 */
@Path("/reservations")
@RequestScoped
public class ReservationResource extends GenericResource<Reservation> {

    @Inject
    private ReservationDAO reservationDAO;

    @PostConstruct
    public void init() {
        setDao(reservationDAO);
    }

}
