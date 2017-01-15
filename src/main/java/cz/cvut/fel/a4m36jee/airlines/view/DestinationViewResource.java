package cz.cvut.fel.a4m36jee.airlines.view;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * View resources for destination.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "destinationViewResource")
public class DestinationViewResource {

    /**
     * Logger.
     */
    @Inject
    private Logger logger;

    /**
     * Service for destinations.
     */
    @Inject
    private DestinationService destinationService;

    /**
     * Find all destinations.
     * @return List of destinations
     * @throws IOException if redirect is unsuccessful
     */
    public List<Destination> getAllDestinations() throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            List<Destination> destinations = destinationService.list();
            logger.info("Destination list received.");
            return  destinations;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during receive destination list!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find destination.
     * @param id destination ID
     * @return destination
     * @throws IOException if redirect is unsuccessful
     */
    public Destination getDestination(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            Destination destination = destinationService.get(id);
            logger.info("Destination with id " + id +" found.");
            return destination;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during find destination with id " + id +"!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Create new destination.
     * @param destination new destination
     * @throws IOException if redirect is unsuccessful
     */
    public void createDestination(final Destination destination) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            destinationService.create(destination);
            logger.info("New destination created.");
            response.sendRedirect("/airlines/destination/");
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during create destination!");
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Update destination.
     * @param destination destination for update
     * @throws IOException if redirect is unsuccessful
     */
    public void updateDestination(final Destination destination) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            destinationService.update(destination);
            logger.info("Destination with id " + destination.getId() + " updated.");
            response.sendRedirect("/airlines/destination/");
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during update destination with id " + destination.getId() + "!");
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Delete destination.
     * @param id destination ID
     * @throws IOException if redirect is unsuccessful
     */
    public void deleteDestination(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            destinationService.delete(id);
            logger.info("Destination with id " + id + " deleted.");
            response.sendRedirect("/airlines/destination/");
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during delete destination with id " + id + "!");
            response.sendRedirect("/airlines/error/");
        }
    }

}
