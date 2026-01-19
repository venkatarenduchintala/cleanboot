package ch.zhaw.ssdd.cleanboot.domain.model;

public record ManufacturerPartNumber(
    String value
) {
    public ManufacturerPartNumber {
        if(value == null) 
            throw new IllegalArgumentException(
                "A MPN must not have a null value");
        if(!value.matches("^[0-9a-zA-Z\\-_.:,()/ ]+$"))
            throw new IllegalArgumentException(
                "MPN must match this regexp: \"^[0-9a-zA-Z\\-_.:,()/ ]+$\" input was: " + value);
        value = value.trim();
    }
}
