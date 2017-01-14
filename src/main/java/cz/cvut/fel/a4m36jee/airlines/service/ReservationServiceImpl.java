package cz.cvut.fel.a4m36jee.airlines.service;


import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.InvalidSeatNumberException;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for {@link Reservation}
 *
 * @author moravja8
 */
@Stateless
@Transactional
public class ReservationServiceImpl implements ReservationService
{

    private final Logger logger;

    private final ReservationDAO reservationDAO;

    private final Event<ReservationCreated> reservationCreatedEvent;

    @Inject
    public ReservationServiceImpl(Logger logger, ReservationDAO reservationDAO, Event<ReservationCreated> reservationCreatedEvent) {
        this.logger = logger;
        this.reservationDAO = reservationDAO;
        this.reservationCreatedEvent = reservationCreatedEvent;
    }

    @Override
    public List<Reservation> list() {
        logger.info("List of all reservations requested.");
        List<Reservation> reservations = reservationDAO.list();
        logger.info("Returning " + reservations.size() + " reservations.");
        return reservations;
    }

    @Override
    public List<Reservation> listByFlightId(final Long flightId) {
        logger.info("All reservations requested for flight with id " + flightId);
        List<Reservation> allReservationsForFlight = reservationDAO.findBy("flight", flightId);
        logger.info("Returning all reservations for flight with id " + flightId + ". " +
                "Returning " + allReservationsForFlight.size() + " reservations.");
        return allReservationsForFlight;
    }

    @Override
    public Reservation get(final Long id) {
        logger.info("Flight with id " + id + " requested.");
        final Reservation reservation = reservationDAO.find(id);
        logger.info("Returning reservation: " + reservation);
        return reservation;
    }

    @Override
    public void create(final Reservation reservation) throws SeatAlreadyReservedException, InvalidSeatNumberException {
        final Flight flight = reservation.getFlight();
        logger.info("Trying to create new Reservation for Flight with id " + flight.getId() + " and seat "
                + reservation.getSeat());

        if (reservation.getSeat() > flight.getSeats() || reservation.getSeat() < 1) {
            throw new InvalidSeatNumberException(reservation);
        }

        List<Reservation> allReservationsForFlight = reservationDAO.findBy("flight", flight.getId());
        for (Reservation flightReservation : allReservationsForFlight) {
            if (flightReservation.getSeat().equals(reservation.getSeat())) {
                throw new SeatAlreadyReservedException(reservation, flightReservation);
            }
        }

        reservationDAO.save(reservation);
        logger.info("Created a new Reservation with id: " + reservation.getId());
        reservationCreatedEvent.fire(new ReservationCreated(reservation));
    }

    @Override
    public void delete(final Long id) {
        logger.info("Deleting Reservation with id " + id);
        reservationDAO.delete(id);
        logger.info("Reservation deleted.");
    }

    @Override
    public void update(final Reservation reservation) {
        logger.info("Updating reservation with id " + reservation.getId());
        reservationDAO.update(reservation);
        logger.info("Reservation updated");
    }
}
