package co.edu.uptc.model.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import co.edu.uptc.config.LoadConfig;
import co.edu.uptc.pojo.Product;

public class ProductValidator implements Function<Product, List<String>> {

    private final LoadConfig config;

    public ProductValidator(LoadConfig config) {
        this.config = config;
    }

    @Override
    public List<String> apply(Product product) {
        List<String> errors = new ArrayList<>();
        errors.add(validatePrice(product.getPrice()));
        boolean hasErrors = errors.stream().anyMatch(s -> s != null && !s.isBlank());
        if (hasErrors) return errors;
        product.setDescription(product.getDescription().toUpperCase());
        return null;
    }

    private String validatePrice(double price) {
        double max = Double.parseDouble(config.get("product.price.max"));
        if (price > max) return "error.price.max|" + max;
        return "";
    }
}
