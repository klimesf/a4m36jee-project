package cz.cvut.fel.a4m36jee.airlines.dao;


import cz.cvut.fel.a4m36jee.airlines.model.AbstractEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author klimefi1
 */
abstract class GenericDAO<T extends AbstractEntity> implements DAO<T> {

    @Inject
    private EntityManager em;

    private Class<T> type;

    GenericDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public List<T> list() {
        return findByPropertyIsNull("deleted");
    }

    @Override
    public T save(final T entity) {
        this.em.persist(entity);
        this.em.flush();
        return entity;
    }

    @Override
    public void delete(final Long id) {
        delete(find(id));
    }

    /**
     * Soft delete entity.
     * @param entity entity
     */
    @Override
    public void delete(final T entity) {
        entity.setDeleted(new Date());
        update(entity);
    }

    @Override
    public T find(final Object id) {
        T entity = this.em.find(type, id);
        return entity != null && entity.getDeleted() == null ? entity : null;
    }

    @Override
    public T update(final T entity) {
        this.em.merge(entity);
        this.em.flush();
        return entity;
    }

    @Override
    public List<T> findBy(String property, Object value) {
        CriteriaQuery<T> select = createQuery(property, value);
        return em.createQuery(select).getResultList();
    }

    @Override
    public List<T> findByPropertyIsNull(String property) {
        CriteriaQuery<T> select = createQueryIsNull(property);
        return em.createQuery(select).getResultList();
    }

    private CriteriaQuery<T> createQuery(String property, Object value) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> from = query.from(type);
        return query
                .select(from)
                .where(
                    criteriaBuilder.equal(from.get(property), value),
                    criteriaBuilder.isNull(from.get("deleted"))
                );
    }

    private CriteriaQuery<T> createQueryIsNull(String property) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
        Root<T> from = query.from(type);
        return query
                .select(from)
                .where(criteriaBuilder.isNull(from.get(property)));
    }

    public EntityManager getEntityManager() {
        return em;
    }

}
