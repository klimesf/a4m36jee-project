package cz.cvut.fel.a4m36jee.airlines.dao;

import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.TestUtils;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
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
public class FlightDaoTest {

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


    private Flight flightA;

    private Long flightIndex;

    @Before
    public void setUp() throws Exception {
        TestUtils testUtils = new TestUtils();
        testUtils.populateDb(reservationDAO, destinationDAO, flightDAO);

        flightIndex = flightDAO.list().get(0).getId();

        flightA = new Flight();
        flightA.setFreeSeats(19);
        flightA.setSeats(20);
        flightA.setName("Let A");
        flightA.setDate(new Date());
        flightA.setFrom(destinationDAO.list().get(0));
        flightA.setTo(destinationDAO.list().get(1));
        flightA.setPrice(500D);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void list() throws Exception {
        Assert.assertEquals(2, flightDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void save() throws Exception {
        flightDAO.save(flightA);
        Assert.assertEquals(3, flightDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void delete() throws Exception {
        flightDAO.delete(flightIndex);
        Assert.assertEquals(1, flightDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteByObject() throws Exception {
        int actualSize = flightDAO.list().size();
        flightDAO.save(flightA);
        Assert.assertEquals(actualSize + 1, flightDAO.list().size());
        flightDAO.delete(flightA);
        Assert.assertEquals(actualSize, flightDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void find() throws Exception {
        Flight flight = flightDAO.find(flightIndex);
        Assert.assertNotNull(flight);
        Assert.assertNotNull(flight.getId());
        Assert.assertNotNull(flight.getDate());
        Assert.assertNotNull(flight.getPrice());
        Assert.assertNotNull(flight.getName());
        Assert.assertNull(flight.getDeleted());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void update() throws Exception {
        Flight flight = flightDAO.find(flightIndex);
        Assert.assertNotNull(flight);
        String newName = "LET 235-4";
        Assert.assertEquals("Let 1", flightDAO.find(flight.getId()).getName());
        flight.setName(newName);
        flightDAO.update(flight);
        Flight loadedFlight = flightDAO.find(flightIndex);
        Assert.assertEquals(flight.getName(), loadedFlight.getName());
        Assert.assertEquals(newName, loadedFlight.getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void findBy() throws Exception {
        flightDAO.save(flightA);
        Flight loadedFlight = flightDAO.findBy("name", flightA.getName()).get(0);
        Assert.assertEquals(flightA.getPrice(), loadedFlight.getPrice());
        Assert.assertEquals(flightA.getSeats(), loadedFlight.getSeats());
    }
}
