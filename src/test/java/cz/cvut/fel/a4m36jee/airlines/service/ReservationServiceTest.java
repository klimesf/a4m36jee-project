package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.ArquillianTest;
import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.InvalidSeatNumberException;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.model.validation.Longitude;
import cz.cvut.fel.a4m36jee.airlines.util.Resource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.util.Date;

/**
 * @author klimefi1
 */
@RunWith(Arquillian.class)
public class ReservationServiceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Destination.class.getPackage())
                .addPackage(DestinationDAO.class.getPackage())
                .addPackage(DestinationServiceImpl.class.getPackage())
                .addPackage(Resource.class.getPackage())
                .addPackage(UserRole.class.getPackage())
                .addPackage(ReservationCreated.class.getPackage())
                .addPackage(SeatAlreadyReservedException.class.getPackage())
                .addPackage(UserRole.class.getPackage())
                .addPackage(Fixtures.class.getPackage())
                .addPackage(Longitude.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("datasource.xml");
    }

    @Inject
    ReservationService reservationService;

    @Inject
    ReservationDAO reservationDAO;

    @Inject
    FlightDAO flightDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Flight flight;

    @Before
    public void setup() {
        flight = Fixtures.createFlight(Fixtures.createDestinations());
        flightDAO.save(flight);
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setFlight(flight);
        reservation.setEmail("john@isis.sy");
        reservation.setPassword("allahu-akbar");
        reservation.setSeat(1);

        reservationService.create(reservation);

        // Check that the reservation was persisted and assigned an ID
        Assert.assertNotNull(reservation.getId());
    }

    @Test
    @Transactional
    public void testCreateWithMissingData() throws Exception {
        Reservation reservation = new Reservation();
        // Missing created, flight, email, password & seat

        thrown.expect(EJBException.class);
        reservationService.create(reservation);
    }

    @Test
    @Transactional
    public void testCreateWithZeroSeatNumber() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setFlight(flight);
        reservation.setEmail("john@isis.sy");
        reservation.setPassword("allahu-akbar");
        reservation.setSeat(0); // Seat number must be greater than 0

        thrown.expect(InvalidSeatNumberException.class);
        reservationService.create(reservation);
    }

    @Test
    @Transactional
    public void testCreateWithSeatNumberTooBig() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setFlight(flight);
        reservation.setEmail("john@isis.sy");
        reservation.setPassword("allahu-akbar");
        reservation.setSeat(101); // Seat number must be smaller than flight.seats

        thrown.expect(InvalidSeatNumberException.class);
        reservationService.create(reservation);
    }

    @Test
    @Transactional
    public void testCreateWithSeatAlreadyTaken() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setFlight(flight);
        reservation.setEmail("john.doe@example.com");
        reservation.setPassword("secret-password");
        reservation.setSeat(100);
        reservationDAO.save(reservation);

        reservation = new Reservation();
        reservation.setCreated(new Date());
        reservation.setFlight(flight);
        reservation.setEmail("john@isis.sy");
        reservation.setPassword("allahu-akbar");
        reservation.setSeat(100); // The same seat

        thrown.expect(SeatAlreadyReservedException.class);
        reservationService.create(reservation);
    }

}
