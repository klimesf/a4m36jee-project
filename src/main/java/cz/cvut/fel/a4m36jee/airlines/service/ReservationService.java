package cz.cvut.fel.a4m36jee.airlines.service;


import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.exception.SeatAlreadyReservedException;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service layer for {@link Reservation}
 *
 * @author moravja8
 */
@Stateless
public class ReservationService implements CrudService<Reservation>{

    @Inject
    private Logger logger;

    @Inject
    private ReservationDAO reservationDAO;

    @Inject
    private Event<ReservationCreated> reservationCreatedEvent;

    @Override
    public List<Reservation> list() {
        logger.info("List of all reservations requested.");
        List<Reservation> reservations = reservationDAO.list();
        logger.info("Returning " + reservations.size() + " reservations.");
        return reservations;
    }

    @Override
    public Reservation get(final Long id) {
        logger.info("Flight with id " + id +  " requested.");
        final Reservation reservation = reservationDAO.find(id);
        logger.info("Returning reservation: " + reservation);
        return reservation;
    }

    @Override
    public void create(final Reservation reservation) throws SeatAlreadyReservedException {
        final Flight flight = reservation.getFlight();
        logger.info("Trying to create new Reservation for Flight with id " + flight.getId() + " and seat "
                + reservation.getSeat());

        List<Reservation> allReservationsForFlight = reservationDAO.findBy("flightId", flight.getId());
        for (Reservation flightReservation : allReservationsForFlight) {
            if (flightReservation.getSeat().equals(reservation.getSeat())) {
                throw  new SeatAlreadyReservedException(reservation, flightReservation);
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
