package cz.cvut.fel.a4m36jee.airlines.service;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;

import java.util.List;

/**
 * Service layer for {@link Destination}
 *
 * @author moravja8
 */
public interface DestinationService extends CrudService<Destination> {

    List<Destination> findByName(String name);
}
