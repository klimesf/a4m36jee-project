package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.Fixtures;
import cz.cvut.fel.a4m36jee.airlines.dao.DestinationDAO;
import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.util.Resource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;

/**
 * @author klimefi1
 */
@RunWith(Arquillian.class)
public class DestinationServiceTest {

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
                .addClass(Fixtures.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("datasource.xml");
    }

    @Inject
    DestinationService destinationService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Destination destination = new Destination();
        destination.setName("Tokyo");
        destination.setLat(35.652832);
        destination.setLon(139.839478);

        destinationService.create(destination);

        // Check that the destinationDAO was persisted and assigned an ID
        Assert.assertNotNull(destination.getId());
    }

    @Test
    @Transactional
    public void testCreateWithMissingData() throws Exception {
        Destination destination = new Destination();
        // Missing name, lat & lon

        thrown.expect(EJBException.class);
        destinationService.create(destination);
    }

}
