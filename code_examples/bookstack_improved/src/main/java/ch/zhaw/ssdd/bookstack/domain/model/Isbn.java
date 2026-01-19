package ch.zhaw.ssdd.bookstack.domain.model;

import java.util.regex.Pattern;

import ch.zhaw.ssdd.bookstack.domain.exception.DomainValidationException;

public record Isbn(
        String value
) {

    private static final Pattern PATTERN = Pattern.compile("^(97(8|9))?\\d{9}(\\d|X)$");

    public Isbn {
        if (value == null)
            throw new IllegalArgumentException("An ISBN must have a non-null value");
        
        value = value.trim();
        value = value.replaceAll("[-\\s]", "").toUpperCase();

        if (!PATTERN.matcher(value).matches())
            throw new DomainValidationException("ISBN has invalid format");
    }
}
