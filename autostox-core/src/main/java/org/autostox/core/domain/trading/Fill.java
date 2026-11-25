package org.autostox.core.domain.trading;

import java.math.BigDecimal;

import org.autostox.core.domain.exception.ValidationException;
import org.autostox.core.domain.instrument.Symbol;

/**
 * Execution of an order.
 *
 * @param timestamp time of the execution
 * @param symbol ticker symbol, e.g. {@code AAPL}
 * @param side whether units were bought or sold
 * @param quantity number of units executed, always positive
 * @param price price per unit, always positive
 */
public record Fill(long timestamp, Symbol symbol, Side side, BigDecimal quantity, BigDecimal price) {

    public Fill {
        if (symbol == null) {
            throw new ValidationException("Symbol must not be null");
        }
        if (side == null) {
            throw new ValidationException("Side must not be null");
        }
        if (quantity == null || quantity.signum() <= 0) {
            throw new ValidationException("Quantity must be positive, but was " + quantity);
        }
        if (price == null || price.signum() <= 0) {
            throw new ValidationException("Price must be positive, but was " + price);
        }
    }

    /**
     * @return total value of the execution, i.e. {@code quantity * price}
     */
    public BigDecimal getValue() {
        return quantity.multiply(price);
    }
}
