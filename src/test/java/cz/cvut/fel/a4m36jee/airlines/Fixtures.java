package cz.cvut.fel.a4m36jee.airlines;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import java.util.Date;

/**
 * Fixtures for Arquillian tests.
 *
 * @author moravja8
 */
public class Fixtures {

    public static class Tuple<X, Y> {
        public final X first;
        public final Y second;

        public Tuple(X first, Y second) {
            this.first = first;
            this.second = second;
        }
    }

    public static Tuple<Destination, Destination> createDestinations() {
        Destination from = new Destination();
        from.setName("Tokyo");
        from.setLat(35.652832);
        from.setLon(139.839478);

        Destination to = new Destination();
        to.setName("Tokyo");
        to.setLat(35.652832);
        to.setLon(139.839478);

        return new Tuple<>(from, to);
    }

    public static Flight createFlight(Tuple<Destination, Destination> destinations) {
        Flight flight = new Flight();
        flight.setName("ABC-0123");
        flight.setDate(new Date());
        flight.setFrom(destinations.first);
        flight.setTo(destinations.second);
        flight.setPrice(1399.);
        flight.setSeats(100);

        return flight;
    }

    public static Reservation createReservation(Flight flight) {
        Reservation reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setPassword("tajneheslo");
        reservation.setSeat(2);
        reservation.setFlight(flight);

        return reservation;
    }

}
