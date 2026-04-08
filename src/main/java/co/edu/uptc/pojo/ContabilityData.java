package co.edu.uptc.pojo;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContabilityData {

    @CsvBindByName(column = "Description")
    private String description;

    @CsvBindByName(column = "TransactionType")
    private TransactionType transactionType;

    @CsvBindByName(column = "Price")
    private double transactionValue;

    @CsvBindByName(column = "Date")
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
