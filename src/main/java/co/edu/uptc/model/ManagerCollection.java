package co.edu.uptc.model;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import co.edu.uptc.util.list.DoubleList;

public class ManagerCollection<T> {

    private final DoubleList<T> list;
    private final BiConsumer<DoubleList<T>, T> insertionStrategy;
    private final Function<T, List<String>> validator;

    public ManagerCollection(
            BiConsumer<DoubleList<T>, T> insertionStrategy,
            Function<T, List<String>> validator) {
        this.list = new DoubleList<>();
        this.insertionStrategy = insertionStrategy;
        this.validator = validator;
    }

    public List<String> add(T element) {
        List<String> errors = validator.apply(element);
        boolean hasErrors = errors != null && errors.stream().anyMatch(s -> s != null && !s.isBlank());
        if (!hasErrors) {
            insertionStrategy.accept(list, element);
            return null;
        }
        return errors;
    }

    public T remove() {
        return list.delete();
    }

    public List<T> listObjects() {
        return list.getAllObjects();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void print() {
        list.getAllObjects().forEach(System.out::println);
    }
}
