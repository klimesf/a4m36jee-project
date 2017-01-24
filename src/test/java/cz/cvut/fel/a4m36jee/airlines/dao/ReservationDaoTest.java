package cz.cvut.fel.a4m36jee.airlines.dao;

import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.TestUtils;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.model.validation.Longitude;
import cz.cvut.fel.a4m36jee.airlines.rest.resource.DestinationResource;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationServiceImpl;
import cz.cvut.fel.a4m36jee.airlines.util.Resource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Date;

/**
 * Tests for DAO.
 *
 * @author moravja8
 */
@RunWith(Arquillian.class)
public class ReservationDaoTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Destination.class.getPackage())
                .addPackage(DestinationDAO.class.getPackage())
                .addPackage(DestinationServiceImpl.class.getPackage())
                .addPackage(ReservationCreated.class.getPackage())
                .addPackage(DestinationResource.class.getPackage())
                .addPackage(Resource.class.getPackage())
                .addPackage(SeatAlreadyReservedException.class.getPackage())
                .addPackage(UserRole.class.getPackage())
                .addPackage(Fixtures.class.getPackage())
                .addPackage(Longitude.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "import.sql")
                .addAsWebInfResource("datasource.xml");
    }

    @Inject
    private DestinationDAO destinationDAO;

    @Inject
    private FlightDAO flightDAO;

    @Inject
    private ReservationDAO reservationDAO;

    private Reservation reservationA;

    @Before
    public void setUp() throws Exception {
        TestUtils testUtils = new TestUtils();
        testUtils.populateDb(reservationDAO, destinationDAO, flightDAO);

        reservationA = new Reservation();
        reservationA.setCreated(new Date());
        reservationA.setFlight(flightDAO.list().get(0));
        reservationA.setPassword("heslo");
        reservationA.setSeat(3);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void list() throws Exception {
        Assert.assertEquals(2, flightDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void save() throws Exception {
        reservationDAO.save(reservationA);
        Assert.assertEquals(2, flightDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void delete() throws Exception {
        reservationDAO.delete(reservationDAO.list().get(0).getId());
        Assert.assertEquals(2, reservationDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteByObject() throws Exception {
        int actualSize = reservationDAO.list().size();
        reservationDAO.save(reservationA);
        Assert.assertEquals(actualSize + 1, reservationDAO.list().size());
        reservationDAO.delete(reservationA);
        Assert.assertEquals(actualSize, reservationDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void find() throws Exception {
        Reservation reservation = reservationDAO.find(reservationDAO.list().get(0).getId());
        Assert.assertNotNull(reservation);
        Assert.assertNotNull(reservation.getId());
        Assert.assertNotNull(reservation.getFlight());
        Assert.assertNotNull(reservation.getSeat());
        Assert.assertNotNull(reservation.getCreated());
        Assert.assertNull(reservation.getDeleted());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void update() throws Exception {
        Reservation reservation = reservationDAO.find(reservationDAO.list().get(0).getId());
        Assert.assertNotNull(reservation);
        Date newCreated = new Date();
        reservation.setCreated(newCreated);
        reservationDAO.update(reservation);
        Reservation loadedReservation = reservationDAO.find(reservationDAO.list().get(0).getId());
        Assert.assertEquals(reservation.getCreated(), loadedReservation.getCreated());
        Assert.assertEquals(newCreated, loadedReservation.getCreated());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void findBy() throws Exception {
        reservationA = reservationDAO.save(reservationA);
        Reservation loadedReservation = reservationDAO.findBy("created", reservationA.getCreated()).get(0);
        Assert.assertEquals(reservationA.getFlight(), loadedReservation.getFlight());
        Assert.assertEquals(reservationA.getSeat(), loadedReservation.getSeat());
    }
}
