package ch.zhaw.ssdd.cleanboot.domain.model;

public record StockKeepingUnit(
    String value
) {
    public StockKeepingUnit {
        if(value == null) 
            throw new IllegalArgumentException(
                "A MPN must not have a null value");
        if(!value.matches("^[0-9a-zA-Z\\-_.:, ]+$"))
            throw new IllegalArgumentException(
                "MPN must match this regexp: \"^[0-9a-zA-Z\\-_.:, ]+$\"");
        value = value.trim();
    }
}
