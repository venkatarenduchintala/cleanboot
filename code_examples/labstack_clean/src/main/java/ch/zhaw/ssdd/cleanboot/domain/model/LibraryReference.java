package ch.zhaw.ssdd.cleanboot.domain.model;

public record LibraryReference(
    String value
) {
    public LibraryReference {
        if(value == null) 
            throw new IllegalArgumentException(
                "A LibraryReference must not have a null value");
        value = value.trim(); 
        if(!value.matches("^[0-9a-zA-Z-_]+:[0-9a-zA-Z-_\\.]+$"))
            throw new IllegalArgumentException(
                "Name must match this regexp: \"^[0-9a-zA-Z-_]+:[0-9a-zA-Z-_\\.]+$\" but was: " + value);
           }
}
