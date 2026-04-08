package co.edu.uptc.pojo;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @CsvBindByName(column = "Id")
    private int id;

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "LastName")
    private String lastName;

    @CsvBindByName(column = "Gender")
    private String gender;

    @CsvBindByName(column = "BirthDate")
    @CsvDate("yyyy-MM-dd")
    private LocalDate birthDate;
}
