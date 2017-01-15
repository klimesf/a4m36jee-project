package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;

import java.util.List;

/**
 * Service layer for {@link Flight}
 *
 * @author moravja8
 */
public interface FlightService extends CrudService<Flight> {

    /**
     * Get all flights for given destination.
     * Both incoming and outcoming destinations are counted.
     * @param destinationId destination id
     * @return flights
     */
    List<Flight> listByDestinationId(Long destinationId);
}
