package ch.zhaw.ssdd.bookstack.domain.model;

import java.util.regex.Pattern;

import ch.zhaw.ssdd.bookstack.domain.exception.DomainValidationException;

public record Title(
        String text) {

    public static final Pattern PATTERN = Pattern.compile("^[\\p{L}0-9 .,:;!?()'\"-]+$");

    public Title {
        if (text == null)
            throw new IllegalArgumentException("A book must have a non-null title");
        text = text.trim();
        if (!PATTERN.matcher(text).matches())
            throw new DomainValidationException("Title has invalid characters.");
        if (text.length() < 4 || text.length() > 60)
            throw new DomainValidationException(
                    "A book's title must be between 4 and 60 characters");
    }
}
