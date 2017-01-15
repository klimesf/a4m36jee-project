package cz.cvut.fel.a4m36jee.airlines.exception;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

/**
 * Exception occurs when trying to delete reservation and tries to authenticate with wrong password.
 *
 * Passwords are not shown anywhere.
 *
 * @author moravja8
 */
public class BadReservationPasswordException extends Exception {
    /**
     * Reservation.
     */
    private Reservation reservation;

    public BadReservationPasswordException(final Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String getMessage() {
        return "Could not delete reservation with id " + reservation.getId() + ", wrong password.";
    }

    @Override
    public String getLocalizedMessage() {
        return super.getMessage();
    }
}
