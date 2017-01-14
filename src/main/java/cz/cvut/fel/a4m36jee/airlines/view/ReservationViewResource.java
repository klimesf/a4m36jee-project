package cz.cvut.fel.a4m36jee.airlines.view;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;
import cz.cvut.fel.a4m36jee.airlines.service.ReservationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * View resources for reservation.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "reservationViewResource")
public class ReservationViewResource {

    /**
     * Logger.
     */
    @Inject
    private Logger logger;

    /**
     * Service for reservations.
     */
    @Inject
    private ReservationService reservationService;

    @Inject
    private FlightService flightService;

    /**
     * Find all reservations.
     * @return List of reservations
     * @throws IOException if redirect is unsuccessful
     */
    public List<Reservation> getAllReservations() throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            List<Reservation> reservations = reservationService.list();
            logger.info("Reservation list received.");
            return  reservations;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during receive reservation list!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find reservations for flight.
     * @param flightId flight ID
     * @return List of reservations
     * @throws IOException if redirect is unsuccessful
     */
    public List<Reservation> getFlightReservation(final long flightId) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            List<Reservation> reservations = reservationService.listByFlightId(flightId); //TODO correct attribute
            logger.info("Reservation list for flight with id " + flightId + " received.");
            return  reservations;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during receive reservation list for flight with id " + flightId + "!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find reservation.
     * @param id reservation ID
     * @return reservation
     * @throws IOException if redirect is unsuccessful
     */
    public Reservation getReservation(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            Reservation reservation = reservationService.get(id);
            logger.info("Reservation with id " + id +" found.");
            return reservation;
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during find reservation with id " + id +"!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Create new reservation.
     * @param reservation new reservation
     * @throws IOException if redirect is unsuccessful
     */
    public void createReservation(final Reservation reservation, final Flight flight) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        reservation.setFlight(flight);
        reservation.setCreated(new Date());
        try {
            reservationService.create(reservation);
            logger.info("New reservation created.");
            response.sendRedirect("/airlines/flight/reservation/?id="+flight.getId());
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during create reservation!");
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Delete reservation.
     * @param id reservation ID
     * @throws IOException if redirect is unsuccessful
     */
    public void deleteReservation(final long id, final long flightId, final String password) throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            reservationService.delete(id); //TODO add password
            logger.info("Reservation with id " + id + " deleted.");
            response.sendRedirect("/airlines/flight/reservation/?id="+flightId);
        } catch (Exception e) { //TODO exception
            logger.severe( "Error during delete reservation with id " + id + "!");
            response.sendRedirect("/airlines/error/");
        }
    }

}
