package cz.cvut.fel.a4m36jee.airlines.service;


import cz.cvut.fel.a4m36jee.airlines.exception.BadReservationPasswordException;
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
    List<Reservation> listByFlightId(final Long flightId);

    /**
     * Smaže instanci s daným id. Kontroluje heslo.
     * @param id id instance
     * @throws BadReservationPasswordException in case password is wrong
     */
    void delete(final Long id, final String password) throws BadReservationPasswordException;
}