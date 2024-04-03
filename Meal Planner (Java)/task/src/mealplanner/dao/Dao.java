package mealplanner.dao;

import java.util.List;

public interface Dao<T> {
    public T add(T entity);
    public void delete(int id);
    public void update(T entity);
    public T get(int id);
    public List<T> getAll(String query);
}
