package cz.cvut.fel.a4m36jee.airlines.controller;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;
import cz.cvut.fel.a4m36jee.airlines.service.ReservationService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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
@Model
public class ReservationController {

    @Inject
    private Logger logger;

    @Inject
    private ReservationService reservationService;

    @Inject
    private FlightService flightService;

    @Inject
    private FacesContext facesContext;

    @Produces
    @Named(value = "newReservation")
    private Reservation newReservation;

    @PostConstruct
    public void initNewReservation() {
        newReservation = new Reservation();
    }

    /**
     * Find all reservations.
     *
     * @return List of reservations
     * @throws IOException if redirect is unsuccessful
     */
    public List<Reservation> getAllReservations() throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            List<Reservation> reservations = reservationService.list();
            logger.info("Reservation list received.");
            return reservations;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during receive reservation list!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find reservations for flight.
     *
     * @param flightId flight ID
     * @return List of reservations
     * @throws IOException if redirect is unsuccessful
     */
    public List<Reservation> getFlightReservation(final long flightId) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            List<Reservation> reservations = reservationService.listByFlightId(flightId); //TODO correct attribute
            logger.info("Reservation list for flight with id " + flightId + " received.");
            return reservations;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during receive reservation list for flight with id " + flightId + "!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Find reservation.
     *
     * @param id reservation ID
     * @return reservation
     * @throws IOException if redirect is unsuccessful
     */
    public Reservation getReservation(final long id) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            Reservation reservation = reservationService.get(id);
            logger.info("Reservation with id " + id + " found.");
            return reservation;
        } catch (Exception e) { //TODO exception
            logger.severe("Error during find reservation with id " + id + "!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException();
        }
    }

    /**
     * Create new Reservation.
     *
     * @throws IOException if redirect is unsuccessful
     */
    public void createReservation(final Flight flight) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        newReservation.setFlight(flight);
        newReservation.setCreated(new Date());
        try {
            reservationService.create(newReservation);
            logger.info("New reservation created.");
            response.sendRedirect("/airlines/flight/reservation/?id=" + flight.getId());
        } catch (Exception e) { //TODO exception
            logger.severe("Error during create reservation!");
            response.sendRedirect("/airlines/error/");
        }
    }

    /**
     * Delete Reservation.
     *
     * @param id reservation ID
     * @throws IOException if redirect is unsuccessful
     */
    public void deleteReservation(final long id, final long flightId, final String password) throws IOException {
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            reservationService.delete(id, password);
            logger.info("Reservation with id " + id + " deleted.");
            response.sendRedirect("/airlines/flight/reservation/?id=" + flightId);
        } catch (Exception e) { //TODO exception
            logger.severe("Error during delete reservation with id " + id + "!");
            response.sendRedirect("/airlines/error/");
        }
    }

}
