package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationService;
import cz.cvut.fel.a4m36jee.airlines.util.Resource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.extension.rest.client.Header;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;


/**
 * @author klimefi1
 */
@RunWith(Arquillian.class)
public class ReservationResourceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Destination.class.getPackage())
                .addPackage(FlightDAO.class.getPackage())
                .addPackage(DestinationService.class.getPackage())
                .addPackage(ReservationCreated.class.getPackage())
                .addPackage(FlightResource.class.getPackage())
                .addPackage(Resource.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "import.sql")
                .addAsWebInfResource("datasource.xml");
    }

    @Inject
    FlightDAO flightDAO;

    @Inject
    DestinationDAO destinationDAO;

    @Inject
    ReservationDAO reservationDAO;

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testList(@ArquillianResteasyResource ReservationResource reservationResource) {
        createReservation();

        final List<Reservation> result = reservationResource.list();

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testGet(@ArquillianResteasyResource ReservationResource reservationResource) {
        Reservation reservation = createReservation();

        final Reservation result = reservationResource.get(reservation.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(reservation.getId(), result.getId());
        Assert.assertEquals(reservation.getSeat(), result.getSeat());
        Assert.assertEquals(reservation.getCreated(), result.getCreated());
    }

    private Reservation createReservation() {
        Flight flight = createFlight();

        Reservation reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setPassword("tajneheslo");
        reservation.setSeat(2);
        reservation.setFlight(flight);
        reservationDAO.save(reservation);

        return reservation;
    }

    private Flight createFlight() {
        Destination from = new Destination();
        from.setName("Tokyo");
        from.setLat(35.652832);
        from.setLon(139.839478);
        destinationDAO.save(from);

        Destination to = new Destination();
        to.setName("Tokyo");
        to.setLat(35.652832);
        to.setLon(139.839478);
        destinationDAO.save(to);

        Flight flight = new Flight();
        flight.setName("ABC-0123");
        flight.setDate(new Date());
        flight.setFrom(from);
        flight.setTo(to);
        flight.setPrice(1399.);
        flight.setSeats(100);
        flightDAO.save(flight);

        return flight;
    }

}
