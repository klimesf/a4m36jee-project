package cz.cvut.fel.a4m36jee.airlines.service;


import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Service layer for {@link Reservation}
 *
 * @author moravja8
 */
@Stateless
public interface ReservationService extends CrudService<Reservation>{

    /**
     * Returns all reservations for given flight.
     * @param flightId flight id
     * @return reservations
     */
    public List<Reservation> listByFlightId(final Long flightId);
}
