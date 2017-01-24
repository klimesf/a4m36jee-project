package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
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
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.util.Date;

/**
 * @author slavion3
 */
@RunWith(Arquillian.class)
public class FlightServiceTest {

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
    FlightService flightService;

    @Inject
    DestinationDAO destinationDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Fixtures.Tuple<Destination, Destination> destinations;

    @Before
    public void setup() {
        destinations = Fixtures.createDestinations();
        destinationDAO.save(destinations.first);
        destinationDAO.save(destinations.second);
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Flight flight = new Flight();
        flight.setDate(new Date());
        flight.setFrom(destinations.first);
        flight.setTo(destinations.second);
        flight.setName("ABC-1234");
        flight.setPrice(199.);
        flight.setSeats(100);

        flightService.create(flight);

        // Check that the flight was persisted and assigned an ID
        Assert.assertNotNull(flight.getId());
    }

    @Test
    @Transactional
    public void testCreateWithMissingData() throws Exception {
        Flight flight = new Flight();
        // Missing date, from, to, name, price & seats

        thrown.expect(EJBException.class);
        flightService.create(flight);
    }

}
