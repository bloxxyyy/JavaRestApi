package nl.han.servicelayer.daos;

import java.util.List;

public interface Dao<T> {

    T get(T entity);

    void add(T t);

    void update(T t);

    void delete(T t);

    List<T> getAll();

    List<T> getAllBy(T t);
}