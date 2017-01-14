package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.dao.FlightDAO;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * @author slavion3
 */
@Stateless
@Transactional
public class FlightService {

    @Inject
    FlightDAO flightDao;

    public List<Flight> getAllFlights() {
        return flightDao.list();
    }

    public Flight getFlight(final long id) {
        Flight flight = flightDao.find(id);
        if (flight == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return flight;
    }

    public void createFlight(final Flight flight) {
        flightDao.save(flight);
    }

    public void updateFlight(final Flight flight) throws IOException {
        flightDao.update(flight);
    }

    public void deleteFlight(final long id) {
        flightDao.delete(id);
    }

}
