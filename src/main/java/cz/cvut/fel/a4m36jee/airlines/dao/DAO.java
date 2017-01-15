package cz.cvut.fel.a4m36jee.airlines.dao;

import cz.cvut.fel.a4m36jee.airlines.model.AbstractEntity;

import java.util.List;

/**
 * @author klimefi1
 */
public interface DAO<T extends AbstractEntity> {

    List<T> list();

    T save(T entity);

    void delete(Long id);

    void delete(T entity);

    T find(Object id);

    T update(T entity);

    List<T> findBy(String attribute, Object value);

    List<T> findByPropertyIsNull(String property);
}
