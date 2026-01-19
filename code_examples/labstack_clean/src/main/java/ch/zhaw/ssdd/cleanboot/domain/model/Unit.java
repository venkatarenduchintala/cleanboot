package ch.zhaw.ssdd.cleanboot.domain.model;

import java.math.BigDecimal;

public enum Unit {

    AMPERE(MeasuredQuantity.CURRENT, "A", BigDecimal.ONE, BigDecimal.ZERO),
    MILLIAMPERE(MeasuredQuantity.CURRENT, "mA", new BigDecimal("0.001"), BigDecimal.ZERO),

    VOLT(MeasuredQuantity.VOLTAGE, "V", BigDecimal.ONE, BigDecimal.ZERO),
    MILLIVOLT(MeasuredQuantity.VOLTAGE, "mV", new BigDecimal("0.001"), BigDecimal.ZERO),
    KILOVOLT(MeasuredQuantity.VOLTAGE, "kV", new BigDecimal("1000"), BigDecimal.ZERO),

    OHM(MeasuredQuantity.RESISTANCE, "R", BigDecimal.ONE, BigDecimal.ZERO),
    KILOOHM(MeasuredQuantity.RESISTANCE, "k", new BigDecimal(1000), BigDecimal.ZERO),
    MEGAOHM(MeasuredQuantity.RESISTANCE, "M", new BigDecimal(1000000), BigDecimal.ZERO),

    MICROFARAD(MeasuredQuantity.CAPACITANCE, "u", new BigDecimal(0.000001), BigDecimal.ZERO),
    NANOFARAD(MeasuredQuantity.CAPACITANCE, "n", new BigDecimal(0.000000001), BigDecimal.ZERO),
    PICOFARAD(MeasuredQuantity.CAPACITANCE, "p", new BigDecimal(0.000000000001), BigDecimal.ZERO),

    MICROHENRY(MeasuredQuantity.INDUCTANCE, "u", new BigDecimal(0.000001), BigDecimal.ZERO),

    KELVIN(MeasuredQuantity.ABSOLUTE_TEMPERATURE, "K", BigDecimal.ONE, BigDecimal.ZERO),
    CELSIUS(MeasuredQuantity.TEMPERATURE, "°C", BigDecimal.ONE, new BigDecimal("273.15")),

    WATT(MeasuredQuantity.POWER, "W", BigDecimal.ONE, BigDecimal.ZERO),
    MILLIWATT(MeasuredQuantity.POWER, "mW", new BigDecimal("0.001"), BigDecimal.ZERO),

    HERTZ(MeasuredQuantity.FREQUENCY, "Hz", BigDecimal.ONE, BigDecimal.ZERO),
    KILOHERTZ(MeasuredQuantity.FREQUENCY, "kHz", new BigDecimal("1000"), BigDecimal.ZERO),
    MEGAHERTZ(MeasuredQuantity.FREQUENCY, "MHz", new BigDecimal("1000000"), BigDecimal.ZERO),

    PERCENT(MeasuredQuantity.DIMENSIONLESS, "%", new BigDecimal("0.01"), BigDecimal.ZERO);

    private final MeasuredQuantity quantity;
    private final String symbol;
    private final BigDecimal scaleToBase;
    private final BigDecimal offsetToBase;

    Unit(MeasuredQuantity quantity, String symbol, BigDecimal scaleToBase, BigDecimal offsetToBase) {
        this.quantity = quantity;
        this.symbol = symbol;
        this.scaleToBase = scaleToBase;
        this.offsetToBase = offsetToBase;
    }

    public MeasuredQuantity quantity() {
        return quantity;
    }

    public String symbol() {
        return symbol;
    }

    public String toString() {
        return name();
    }

    public static Unit parseString(String s) {
        for (Unit u : values()) {
            if (u.toString().equalsIgnoreCase(s)) {
                return u;
            }
        }
        throw new IllegalArgumentException("Unknown Property: " + s);
    }

    /** Wert in Basis-Einheit umrechnen */
    public BigDecimal toBase(BigDecimal value) {
        return value.multiply(scaleToBase).add(offsetToBase);
    }

    /** Wert aus Basis-Einheit zurückrechnen */
    public BigDecimal fromBase(BigDecimal baseValue) {
        return baseValue.subtract(offsetToBase).divide(scaleToBase);
    }
}
