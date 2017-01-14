package cz.cvut.fel.a4m36jee.airlines.service;

import java.util.List;

/**
 * Interface for CRUD operations for services.
 *
 * @author moravja8
 */
public interface CrudService<T> {

    /**
     * List all stored instances of class T.
     *
     * @return list of instances
     */
    List<T> list();

    /**
     * Get instance.
     *
     * @param id instance id
     * @return instance
     */
    T get(final Long id);

    /**
     * Create new instance of class T.
     *
     * @param instance instance
     */
    void create(final T instance) throws Exception;
}
