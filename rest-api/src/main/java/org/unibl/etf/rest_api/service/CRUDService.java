package org.unibl.etf.rest_api.service;

import java.util.List;

public interface CRUDService<T> {
    T create(T t);
    T update(T t);

    T retrieve(int id);
    List<T> retrieveAll();

    boolean delete(T t);
    default boolean delete(int id) { return delete(retrieve(id)); }
}
