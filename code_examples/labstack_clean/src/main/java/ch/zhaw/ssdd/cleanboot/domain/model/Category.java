package ch.zhaw.ssdd.cleanboot.domain.model;

public enum Category {
    RESISTOR,
    CAPACITOR,
    INDUCTOR,
    FILTER,
    TRANSFORMER,
    FUSE,
    MECHANICAL,
    OPTICAL,
    ESD,
    CRYSTAL,
    IC,
    CONNECTOR,
    DIODE,
    TRANSISTOR;

    public static Category parseString(String s) {
        for (Category c : values()) {
            if (c.toString().equalsIgnoreCase(s)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown Category: " + s);
    }

    public String toString() {
        return name();
    }
}
