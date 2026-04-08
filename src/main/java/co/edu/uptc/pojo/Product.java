package co.edu.uptc.pojo;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @CsvBindByName(column = "Id")
    private int id;

    @CsvBindByName(column = "Description")
    private String description;

    @CsvBindByName(column = "UnitOfMeasure")
    private UnitOfMeasure unitOfMeasure;

    @CsvBindByName(column = "Price")
    private double price;
}
