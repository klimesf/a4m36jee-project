package cz.cvut.fel.a4m36jee.airlines.event;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

/**
 * Event fired when reservation is deleted.
 *
 * Event is sent to JSF via websocket.
 *
 * @author moravja8
 */
public class ReservationDeleted {

    private Reservation reservation;

    public ReservationDeleted(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
