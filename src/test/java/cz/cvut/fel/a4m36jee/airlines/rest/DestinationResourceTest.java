package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.event.DestinationCreated;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
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
import java.util.List;


/**
 * @author klimefi1
 */
@RunWith(Arquillian.class)
public class DestinationResourceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Destination.class.getPackage())
                .addPackage(DestinationDAO.class.getPackage())
                .addPackage(DestinationCreation.class.getPackage())
                .addPackage(DestinationCreated.class.getPackage())
                .addPackage(DestinationResource.class.getPackage())
                .addPackage(Resource.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "import.sql")
                .addAsWebInfResource("datasource.xml");
    }

    @Inject
    DestinationDAO destinationDAO;

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testList(@ArquillianResteasyResource DestinationResource destinationResource) {
        Destination destination = new Destination();
        destination.setName("Tokyo");
        destination.setLat(35.652832);
        destination.setLon(139.839478);
        destinationDAO.save(destination);

        final List<Destination> result = destinationResource.list();

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    @Header(name = "Content-type", value = "application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional(TransactionMode.ROLLBACK)
    public void testGet(@ArquillianResteasyResource DestinationResource destinationResource) {
        Destination destination = new Destination();
        destination.setName("Tokyo");
        destination.setLat(35.652832);
        destination.setLon(139.839478);
        destinationDAO.save(destination);

        final Destination result = destinationResource.get(destination.getId());

        Assert.assertNotNull(result);
        Assert.assertEquals(destination.getId(), result.getId());
        Assert.assertEquals(destination.getLat(), result.getLat());
        Assert.assertEquals(destination.getLon(), result.getLon());
    }

}
