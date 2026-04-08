package co.edu.uptc.model;

import java.util.List;

import co.edu.uptc.pojo.ContabilityData;
import co.edu.uptc.pojo.TransactionType;

public class AccountingCalculator {

    public double calculateNetTotal(List<ContabilityData> records) {
        double total = 0;
        for (ContabilityData record : records) {
            total = applyMovement(total, record);
        }
        return total;
    }

    private double applyMovement(double total, ContabilityData record) {
        if (record.getTransactionType() == TransactionType.INCOME) {
            return total + record.getTransactionValue();
        }
        return total - record.getTransactionValue();
    }
}
