package cz.cvut.fel.a4m36jee.airlines;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import java.util.Date;

/**
 * @author moravja8
 */
public class TestUtils {

    public void populateDb(ReservationDAO reservationDAO, DestinationDAO destinationDAO, FlightDAO flightDAO) {
        Destination destination1 = new Destination();
        Destination destination2 = new Destination();
        Destination destination3 = new Destination();

        destination1.setLat(23D);
        destination1.setLon(42D);
        destination1.setName("Bratislava");

        destination2.setLat(12D);
        destination2.setLon(40D);
        destination2.setName("Berlin");

        destination3.setLat(20D);
        destination3.setLon(40D);
        destination3.setName("Warsava");

        destination1 = destinationDAO.save(destination1);
        destination2 = destinationDAO.save(destination2);
        destination3 = destinationDAO.save(destination3);

        Flight flight1 = new Flight();
        flight1.setFreeSeats(19);
        flight1.setSeats(20);
        flight1.setName("Let 1");
        flight1.setDate(new Date());
        flight1.setFrom(destination3);
        flight1.setTo(destination1);
        flight1.setPrice(300D);
        flightDAO.save(flight1);

        Flight flight2 = new Flight();
        flight2.setFreeSeats(19);
        flight2.setSeats(20);
        flight2.setName("Let A");
        flight2.setDate(new Date());
        flight2.setFrom(destination1);
        flight2.setTo(destination2);
        flight2.setPrice(500D);

        flight1 = flightDAO.save(flight1);
        flight2 = flightDAO.save(flight2);

        Reservation reservation1 = new Reservation();
        reservation1.setSeat(1);
        reservation1.setPassword("heslo");
        reservation1.setFlight(flight1);
        reservation1.setCreated(new Date());

        Reservation reservation2 = new Reservation();
        reservation2.setSeat(2);
        reservation2.setPassword("heslo");
        reservation2.setFlight(flight2);
        reservation2.setCreated(new Date());

        Reservation reservation3 = new Reservation();
        reservation3.setSeat(1);
        reservation3.setPassword("heslo");
        reservation3.setFlight(flight2);
        reservation3.setCreated(new Date());

        reservationDAO.save(reservation1);
        reservationDAO.save(reservation2);
        reservationDAO.save(reservation3);
    }

}
