package cz.cvut.fel.a4m36jee.airlines.service;


import cz.cvut.fel.a4m36jee.airlines.exception.BadReservationPasswordException;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import java.util.List;

/**
 * Service layer for {@link Reservation}
 *
 * @author moravja8
 */
public interface ReservationService extends CrudService<Reservation>{

    /**
     * Returns all reservations for given flight.
     * @param flightId flight id
     * @return reservations
     */
    List<Reservation> listByFlightId(final Long flightId);

    /**
     * Delete reservation with checked password.
     * @param id reservaton id
     * @param password password
     * @throws BadReservationPasswordException if password does not match
     */
    public void delete(final Long id, final String password) throws BadReservationPasswordException;
}
