package ch.zhaw.ssdd.cleanboot.domain.model;

public enum Distributor {
    MOUSER, DIGIKEY, FARNELL, LCSC;

    @Override
    public String toString() {
        return name();
    }

    public static Distributor fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Distributor value cannot be null");
        }

        for (Distributor distributor : Distributor.values()) {
            if (distributor.name().equalsIgnoreCase(value.trim())) {
                return distributor;
            }
        }

        throw new IllegalArgumentException("Unknown distributor: " + value);
    }
}

