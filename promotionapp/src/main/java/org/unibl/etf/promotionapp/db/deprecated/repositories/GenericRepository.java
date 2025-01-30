package org.unibl.etf.promotionapp.db.deprecated.repositories;

import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T entity);
    T update(T entity);
    void delete(T entity);
    T findById(ID id);
    List<T> findAll();
}
