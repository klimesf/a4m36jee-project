package cz.cvut.fel.a4m36jee.airlines.dao;

import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.TestUtils;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
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

/**
 * Tests for DAO.
 *
 * @author moravja8
 */
@RunWith(Arquillian.class)
public class DestionationDaoTest {

    @Inject
    private DestinationDAO destinationDAO;

    @Inject
    private ReservationDAO reservationDAO;

    @Inject
    private FlightDAO flightDAO;

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

    private Destination destinationA;
    private Destination destinationB;

    private Long queryIndex;

    @Before
    public void setUp() throws Exception {

        TestUtils testUtils = new TestUtils();
        testUtils.populateDb(reservationDAO, destinationDAO, flightDAO);

        destinationA = new Destination();
        destinationA.setLat(15D);
        destinationA.setLon(42D);
        destinationA.setName("Praha");

        destinationB = new Destination();
        destinationB.setLat(16D);
        destinationB.setLon(40D);
        destinationB.setName("Brno");

        queryIndex = destinationDAO.list().get(1).getId();
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void list() throws Exception {
        Assert.assertEquals(3, destinationDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void save() throws Exception {
        destinationDAO.save(destinationA);
        Assert.assertEquals(4, destinationDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void delete() throws Exception {
        destinationDAO.delete(1L);
        Assert.assertEquals(2, destinationDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void deleteByObject() throws Exception {
        destinationDAO.save(destinationA);
        Assert.assertEquals(4, destinationDAO.list().size());
        destinationDAO.delete(destinationA);
        Assert.assertEquals(3, destinationDAO.list().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void find() throws Exception {
        Destination destination = destinationDAO.find(queryIndex);
        Assert.assertNotNull(destination);
        Assert.assertNotNull(destination.getId());
        Assert.assertNotNull(destination.getLat());
        Assert.assertNotNull(destination.getLon());
        Assert.assertNotNull(destination.getName());
        Assert.assertNull(destination.getDeleted());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void update() throws Exception {
        Destination destination = destinationDAO.find(queryIndex);
        Assert.assertNotNull(destination);
        String newName = "Pelhřimov";
        Assert.assertEquals("Berlin", destinationDAO.find(destination.getId()).getName());
        destination.setName(newName);
        destinationDAO.update(destination);
        Destination lodadedDestination = destinationDAO.find(queryIndex);
        Assert.assertEquals(destination.getName(), lodadedDestination.getName());
        Assert.assertEquals(newName, lodadedDestination.getName());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void findBy() throws Exception {
        destinationDAO.save(destinationB);
        Destination loadedDestination = destinationDAO.findBy("name", destinationB.getName()).get(0);
        Assert.assertEquals(destinationB.getLon(), loadedDestination.getLon());
        Assert.assertEquals(destinationB.getLat(), loadedDestination.getLat());
    }
}
