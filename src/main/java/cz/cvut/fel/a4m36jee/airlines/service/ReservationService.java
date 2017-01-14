package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author slavion3
 */
@Stateless
@Transactional
public class ReservationService {

    @Inject
    ReservationDAO reservationDAO;

    public List<Reservation> list() {
        return reservationDAO.list();
    }

    public List<Reservation> listByFlightId(final Long flightId) {
        return reservationDAO.findBy("flight", flightId);
    }

    public Reservation get(final long id) {
        Reservation reservation = reservationDAO.find(id);
        if (reservation == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return reservation;
    }

    public void create(final Reservation reservation) {
        reservationDAO.save(reservation);
    }

    public void delete(final long id, final String password) {
        reservationDAO.delete(id);
    }

}
