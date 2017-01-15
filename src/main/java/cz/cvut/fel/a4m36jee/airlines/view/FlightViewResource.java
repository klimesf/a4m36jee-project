package cz.cvut.fel.a4m36jee.airlines.view;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;

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
 * View resources for flight.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "flightViewResource")
public class FlightViewResource {

    /**
     * Logger.
     */
    @Inject
    private Logger logger;

    /**
     * Service for flights.
     */
    @Inject
    private FlightService flightService;

    /**
     * Find all flights.
     * @return List of flights
     * @throws IOException if redirect is unsuccessful
     */
    public List<Flight> getAllFlights() throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            List<Flight> reservations = flightService.list();
            logger.info("Flight list received.");
            return  reservations;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during receive flight list!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find flight.
     * @param id flight ID
     * @return flight
     * @throws IOException if redirect is unsuccessful
     */
    public Flight getFlight(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            Flight reservation = flightService.get(id);
            logger.info("Flight with id " + id +" found.");
            return reservation;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during find flight with id " + id +"!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Create new flight.
     * @param flight new flight
     * @throws IOException if redirect is unsuccessful
     */
    public void createFlight(final Flight flight) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            flightService.create(flight);
            logger.info("New flight created.");
            response.sendRedirect("/airlines/flight/");
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during create flight!");
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Update flight.
     * @param flight flight for update
     * @throws IOException if redirect is unsuccessful
     */
    public void updateFlight(Flight flight) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            flightService.update(flight);
            logger.info("Flight with id " + flight.getId() + " updated.");
            response.sendRedirect("/airlines/flight/");
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during update destination with id " + flight.getId() + "!");
            response.sendRedirect("/airlines/error/");
        }
        flightService.update(flight);
    }

    /**
     * Delete flight.
     * @param id flight ID
     * @throws IOException if redirect is unsuccessful
     */
    public void deleteFlight(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            flightService.delete(id);
            logger.info("Flight with id " + id + " deleted.");
            response.sendRedirect("/airlines/flight/");
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during delete flight with id " + id + "!");
            response.sendRedirect("/airlines/error/");
        }
    }

}
