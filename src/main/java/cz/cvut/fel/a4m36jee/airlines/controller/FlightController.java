package cz.cvut.fel.a4m36jee.airlines.controller;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;

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
 * View resources for newFlight.
 *
 * @author slavion3
 */
@Model
public class FlightController {

    @Inject
    private Logger logger;

    @Inject
    private FlightService flightService;

    @Inject
    private FacesContext facesContext;

    @Produces
    @Named(value = "newFlight")
    private Flight newFlight;

    @PostConstruct
    public void initNewFlight() {
        newFlight = new Flight();
    }

    /**
     * Find all flights.
     *
     * @return List of flights
     * @throws IOException if redirect is unsuccessful
     */
    public List<Flight> getAllFlights() throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            List<Flight> reservations = flightService.list();
            logger.info("Flight list received.");
            return reservations;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during receive newFlight list!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find Flight.
     *
     * @param id Flight ID
     * @return newFlight
     * @throws IOException if redirect is unsuccessful
     */
    public Flight getFlight(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            Flight reservation = flightService.get(id);
            logger.info("Flight with id " + id + " found.");
            return reservation;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during find newFlight with id " + id + "!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Create new Flight.
     *
     * @throws IOException if redirect is unsuccessful
     */
    public void createFlight() throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            flightService.create(newFlight);
            logger.info("New newFlight created.");
            response.sendRedirect("/airlines/newFlight/");
        } catch (Exception e) { //TODO exception
            logger.severe("Error during create newFlight!");
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Update Flight.
     *
     * @param flight Flight for update
     * @throws IOException if redirect is unsuccessful
     */
    public void updateFlight(Flight flight) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            flightService.update(flight);
            logger.info("Flight with id " + flight.getId() + " updated.");
            response.sendRedirect("/airlines/newFlight/");
        } catch (Exception e) { //TODO exception
            logger.severe("Error during update destination with id " + flight.getId() + "!");
            response.sendRedirect("/airlines/error/");
        }
        flightService.update(flight);
    }

    /**
     * Delete Flight.
     *
     * @param id newFlight ID
     * @throws IOException if redirect is unsuccessful
     */
    public void deleteFlight(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            flightService.delete(id);
            logger.info("Flight with id " + id + " deleted.");
            response.sendRedirect("/airlines/newFlight/");
        } catch (Exception e) { //TODO exception
            logger.severe("Error during delete newFlight with id " + id + "!");
            response.sendRedirect("/airlines/error/");
        }
    }

}
