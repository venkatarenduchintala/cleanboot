package ch.zhaw.ssdd.cleanboot.domain.model;

public enum SpecifiedProperty {

    OPERATING_TEMPERATURE_MIN(MeasuredQuantity.TEMPERATURE),
    OPERATING_TEMPERATURE_MAX(MeasuredQuantity.TEMPERATURE),

    OPERATING_VOLTAGE_MAX(MeasuredQuantity.VOLTAGE),
    OPERATING_CURRENT_RMS_MAX(MeasuredQuantity.CURRENT),
    SATURATION_CURRENT(MeasuredQuantity.CURRENT),
    SUPPLY_VOLTAGE_MIN(MeasuredQuantity.VOLTAGE),
    SUPPLY_VOLTAGE_MAX(MeasuredQuantity.VOLTAGE),

    POWER_DISSIPATION_MAX(MeasuredQuantity.POWER),

    CLOCK_FREQUENCY_MAX(MeasuredQuantity.FREQUENCY),
    HOLDING_CURRENT(MeasuredQuantity.CURRENT),
    NOMINAL_RESISTANCE(MeasuredQuantity.RESISTANCE),
    NOMINAL_CAPACITANCE(MeasuredQuantity.CAPACITANCE),
    NOMINAL_INDUCTANCE(MeasuredQuantity.INDUCTANCE),
    NOMINAL_IMPEDANCE(MeasuredQuantity.RESISTANCE),
    
    NOMINAL_FREQUENCY(MeasuredQuantity.FREQUENCY);
    

    private final MeasuredQuantity quantity;

    SpecifiedProperty(MeasuredQuantity quantity) {
        this.quantity = quantity;
    }

    public MeasuredQuantity quantity() {
        return quantity;
    }

    public String toString() {
        return name();
    }

    public static SpecifiedProperty parseString(String s) {
        for (SpecifiedProperty p : values()) {
            if (p.toString().equalsIgnoreCase(s)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown Property: " + s);
    }
}
