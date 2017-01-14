package cz.cvut.fel.a4m36jee.airlines.exception;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

/**
 * Exception thrown in case that new {@link Reservation } is tried to be created for
 * seat with invalid number.
 *
 * @author klimefi1
 */
public class InvalidSeatNumberException extends Exception {

    private Reservation reservation;

    public InvalidSeatNumberException(final Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String getMessage() {
        String message = "Cannot create new reservation for flight with id: " + reservation.getFlight().getId() + "\n";
        message += "Seat " + reservation.getSeat() + " is not on the flight\n";
        message += super.getMessage();

        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
}
