package cz.cvut.fel.a4m36jee.airlines.dao;

import java.util.List;

/**
 * @author klimefi1
 */
public interface DAO<T> {

    List<T> list();

    T save(T entity);

    void delete(Object id);

    T find(Object id);

    T update(T entity);

    List<T> findBy(String attribute, Object value);
}
