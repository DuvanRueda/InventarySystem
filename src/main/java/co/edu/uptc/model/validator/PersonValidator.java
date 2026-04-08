package co.edu.uptc.model.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import co.edu.uptc.config.LoadConfig;
import co.edu.uptc.pojo.Person;

public class PersonValidator implements Function<Person, List<String>> {

    private final LoadConfig config;

    public PersonValidator(LoadConfig config) {
        this.config = config;
    }

    @Override
    public List<String> apply(Person person) {
        List<String> errors = new ArrayList<>();
        errors.add(validateName(person.getName()));
        errors.add(validateLastName(person.getLastName()));
        boolean hasErrors = errors.stream().anyMatch(s -> s != null && !s.isBlank());
        return hasErrors ? errors : null;
    }

    private String validateName(String name) {
        int min = Integer.parseInt(config.get("person.name.length.min"));
        int max = Integer.parseInt(config.get("person.name.length.max"));
        if (name.length() < min) return "error.name.min|" + min;
        if (name.length() > max) return "error.name.max|" + max;
        return "";
    }

    private String validateLastName(String lastName) {
        int min = Integer.parseInt(config.get("person.lastname.length.min"));
        int max = Integer.parseInt(config.get("person.lastname.length.max"));
        if (lastName.length() < min) return "error.lastname.min|" + min;
        if (lastName.length() > max) return "error.lastname.max|" + max;
        return "";
    }
}
