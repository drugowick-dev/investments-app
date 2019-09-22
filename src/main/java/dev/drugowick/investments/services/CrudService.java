package dev.drugowick.investments.services;

import java.util.Set;

/**
 * This is an interface, a basic interface for everything Crud
 * on this application.
 *
 * @param <T> Using Java Generics we receive a Type here...
 * @param <ID> ... and an ID here.
 */
public interface CrudService<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
