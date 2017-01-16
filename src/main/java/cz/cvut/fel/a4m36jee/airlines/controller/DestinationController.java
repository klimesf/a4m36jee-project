package cz.cvut.fel.a4m36jee.airlines.controller;

import cz.cvut.fel.a4m36jee.airlines.exception.ErrorWhileContactingWeatherAPIException;
import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.rest.client.OpenWeatherMapClient;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * View resources for newDestination.
 *
 * @author slavion3
 */
@Model
public class DestinationController {

    @Inject
    private Logger logger;

    @Inject
    private DestinationService destinationService;

    @Inject
    private FacesContext facesContext;

    @Inject
    OpenWeatherMapClient weatherClient;

    @Produces
    @Named(value = "newDestination")
    private Destination newDestination;

    @PostConstruct
    public void initNewDestination() {
        newDestination = new Destination();
    }

    /**
     * Find all destinations.
     *
     * @return List of destinations
     * @throws IOException if redirect is unsuccessful
     */
    public List<Destination> getAllDestinations() throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            List<Destination> destinations = destinationService.list();
            logger.fine("Destination list received.");
            return destinations;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during getAllDestinations() list: " + e.getMessage());
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find Destination.
     *
     * @param id Destination ID
     * @return Destination
     * @throws IOException if redirect is unsuccessful
     */
    public Destination getDestination(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            Destination destination = destinationService.get(id);
            logger.fine("Destination with id " + id + " found.");
            return destination;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during getDestination() " + id + ": " + e.getMessage());
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Create new Destination.
     *
     * @throws IOException if redirect is unsuccessful
     */
    public void createDestination() throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            destinationService.create(newDestination);
            logger.info("New newDestination with id " + newDestination.getId() + " created.");
            response.sendRedirect("/airlines/destination/");
        } catch (Exception e) { //TODO exception
            logger.severe("Error during createDestination(): " + e.getMessage());
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Update Destination.
     *
     * @throws IOException if redirect is unsuccessful
     */
    public void updateDestination() throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            destinationService.update(newDestination);
            logger.info("Destination with id " + newDestination.getId() + " updated.");
            response.sendRedirect("/airlines/destination/?faces-redirect=true");
        } catch (Exception e) { //TODO exception
            logger.severe("Error during update newDestination with id " + newDestination.getId() + ": " + e.getMessage());
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Delete Destination.
     *
     * @param id newDestination ID
     * @throws IOException if redirect is unsuccessful
     */
    public void deleteDestination(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            destinationService.delete(id);
            logger.info("Destination with id " + id + " deleted.");
            response.sendRedirect("/airlines/destination/?faces-redirect=true");
        } catch (Exception e) {
            logger.severe("Error during deleting newDestination with id " + id + ": " + e.getMessage());
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Retrieves weather for Destination.
     *
     * @param destination
     */
    public OpenWeatherMapClient.WeatherResponse getWeather(Destination destination) {
        try {
            return weatherClient.getWeather(destination);
        } catch (ErrorWhileContactingWeatherAPIException e) {
            logger.severe("Error while contacting weather API.");
        }
        return null;
    }

}
