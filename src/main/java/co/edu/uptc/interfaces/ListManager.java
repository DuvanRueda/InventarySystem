package co.edu.uptc.interfaces;

import java.util.List;

public interface ListManager<T> {
    List<String> add(T newEntity);
    List<T> listObjects();
    void toCSV();
}
