package ch.zhaw.ssdd.cleanboot.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record UnitPrice(
    BigDecimal value,
    Currency currency
) {
    public static final BigDecimal MAX_PRICE = new BigDecimal(1000);

    public UnitPrice {
        if(value == null || value.signum() < 0) {
            throw new IllegalArgumentException(
                "A Price must have a positive value");
        }
       
        // We always want to have 4 decimal places in pricing information
        value = value.setScale(4, RoundingMode.HALF_UP);
        
        if(value.compareTo(new BigDecimal("0.0001")) < 0) {
            throw new IllegalArgumentException(
                "A single item price must be >= 0.0001 in any currency");
        }

        if(value.compareTo(MAX_PRICE) > 0) {
            throw new IllegalArgumentException(
                "A single item price must be <= " + MAX_PRICE + " in any currency");
        }
        if(currency == null) {
            throw new IllegalArgumentException(
                "A Price must specify its currency");
        }

    }
}
