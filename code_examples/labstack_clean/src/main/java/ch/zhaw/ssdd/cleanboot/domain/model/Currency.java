package ch.zhaw.ssdd.cleanboot.domain.model;

public enum Currency {
    CHF, EUR, USD;

    public static Currency parseString(String s) {
        for (Currency c : values()) {
            if (c.toString().equalsIgnoreCase(s)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown Currency: " + s);
    }

    public String toString() {
        return name();
    }
}
