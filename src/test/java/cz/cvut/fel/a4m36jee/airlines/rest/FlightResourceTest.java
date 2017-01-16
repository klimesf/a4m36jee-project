package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.validation.Longitude;
import cz.cvut.fel.a4m36jee.airlines.rest.resource.FlightResource;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author klimefi1
 */
@RunWith(Arquillian.class)
public class FlightResourceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Flight.class.getPackage())
                .addPackage(FlightDAO.class.getPackage())
                .addPackage(FlightService.class.getPackage())
                .addPackage(FlightResource.class.getPackage())
                .addPackage(ReservationCreated.class.getPackage())
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
    FlightDAO flightDAO;

    @Inject
    DestinationDAO destinationDAO;

    private Flight flight;

    @Before
    public void setup() {
        Fixtures.Tuple<Destination, Destination> destinations = Fixtures.createDestinations();
        destinationDAO.save(destinations.first);
        destinationDAO.save(destinations.second);

        flight = Fixtures.createFlight(destinations);
        flightDAO.save(flight);
    }

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testList(@ArquillianResteasyResource FlightResource flightResource) {
        final List<Flight> result = flightResource.list();
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testGet(@ArquillianResteasyResource FlightResource flightResource) {
        final Flight result = flightResource.get(flight.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(flight.getId(), result.getId());
        Assert.assertEquals(flight.getDate(), result.getDate());
        Assert.assertEquals(flight.getName(), result.getName());
    }

}
