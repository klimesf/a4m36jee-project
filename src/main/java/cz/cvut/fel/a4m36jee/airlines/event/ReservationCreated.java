package cz.cvut.fel.a4m36jee.airlines.event;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

/**
 * Event fired when new reservation created.
 *
 * Event is subscribed by JMS implementation.
 *
 * @author klimefi1, moravja8
 */
public class ReservationCreated {

    private Reservation reservation;

    public ReservationCreated(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
