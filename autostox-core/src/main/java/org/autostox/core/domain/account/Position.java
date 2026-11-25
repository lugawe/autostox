package org.autostox.core.domain.account;

import java.math.BigDecimal;

import org.autostox.core.domain.exception.ValidationException;
import org.autostox.core.domain.instrument.Symbol;

/**
 * Units of an instrument currently held.
 *
 * @param symbol ticker symbol, e.g. {@code AAPL}
 * @param quantity number of units held, always positive
 * @param averagePrice average price paid per unit
 */
public record Position(Symbol symbol, BigDecimal quantity, BigDecimal averagePrice) {

    public Position {
        if (symbol == null) {
            throw new ValidationException("Symbol must not be null");
        }
        if (quantity == null || quantity.signum() <= 0) {
            throw new ValidationException("Quantity must be positive, but was " + quantity);
        }
        if (averagePrice == null || averagePrice.signum() <= 0) {
            throw new ValidationException("Average price must be positive, but was " + averagePrice);
        }
    }
}
