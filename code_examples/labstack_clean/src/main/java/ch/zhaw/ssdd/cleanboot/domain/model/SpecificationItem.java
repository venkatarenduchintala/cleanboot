package ch.zhaw.ssdd.cleanboot.domain.model;

import java.math.BigDecimal;

public record SpecificationItem(
        SpecifiedProperty property,
        BigDecimal value,
        Unit unit) {
    public SpecificationItem {
        if (property == null)
            throw new IllegalArgumentException(
                    "A SpecificationItem must have a property");
        if (value == null)
            throw new IllegalArgumentException(
                    "A SpecificationItem must have a value");
        if (unit == null)
            throw new IllegalArgumentException(
                    "A SpecificationItem must have a unit");
        if (unit.quantity() != property.quantity()) {
            throw new IllegalArgumentException(
                    "Unit " + unit + " does not match Quantity " + property.quantity());
        }
        BigDecimal baseValue = unit.toBase(value);
        property.quantity().validate(baseValue);
    }

    public BigDecimal valueInBaseUnit() {
        return unit.toBase(value);
    }
}
