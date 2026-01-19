package ch.zhaw.ssdd.cleanboot.domain.model;

public record InternalPartNumber(String value
) {
    public InternalPartNumber {
        if(value == null) 
            throw new IllegalArgumentException(
                "An InternalPartNumber must not have a null value");
        if(!value.matches("^[0-9a-zA-Z\\-_.:, ]+$"))
            throw new IllegalArgumentException(
                "An IPN must match this regexp: \"^[0-9a-zA-Z\\-_.:, ]+$\"");
        value = value.trim();
    }
}
