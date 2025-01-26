package org.unibl.etf.rest_api.service;

public interface CRUDService<T> {
    T create(T t);
    T update(T t);
    boolean delete(T t);
    T retrieve(int id);
}
