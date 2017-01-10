package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.event.DestinationCreated;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationCreation;
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
public class FlightResourceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Destination.class.getPackage())
                .addPackage(FlightDAO.class.getPackage())
                .addPackage(DestinationCreation.class.getPackage())
                .addPackage(DestinationCreated.class.getPackage())
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

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testList(@ArquillianResteasyResource FlightResource flightResource) {
        createFlight();

        final List<Flight> result = flightResource.list();

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testGet(@ArquillianResteasyResource FlightResource flightResource) {
        Flight flight = createFlight();

        final Flight result = flightResource.get(flight.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(flight.getId(), result.getId());
        Assert.assertEquals(flight.getDate(), result.getDate());
        Assert.assertEquals(flight.getName(), result.getName());
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
