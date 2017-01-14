package cz.cvut.fel.a4m36jee.airlines.exception;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

/**
 * Exception thrown in case that new {@link Reservation } is tried to be created for
 * already reserved seat.
 *
 * @author moravja8
 */
public class SeatAlreadyReservedException extends Exception {

    private Reservation reservation;
    private Reservation existingReservation;

    public SeatAlreadyReservedException(final Reservation reservation, final Reservation existingReservation) {
        this.reservation = reservation;
        this.existingReservation = existingReservation;
    }

    @Override
    public String getMessage() {
        String message = "Cannot create new reservation for flight with id: " + reservation.getFlight().getId() + "\n";
        message += "Seat " + reservation.getSeat() + " is already reserved " +
                "by reservation with id " + existingReservation.getId() + "\n";
        message += super.getMessage();

        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
}
