package org.autostox.core.domain.instrument;

import java.util.Locale;

import org.autostox.core.domain.exception.ValidationException;

/**
 * International Securities Identification Number (ISO 6166), normalized to upper case without surrounding whitespace.
 *
 * @param value the normalized ISIN, e.g. {@code US0378331005}
 */
public record Isin(String value) {

    public Isin {
        if (value == null || value.isBlank()) {
            throw new ValidationException("ISIN must not be blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return value;
    }
}
