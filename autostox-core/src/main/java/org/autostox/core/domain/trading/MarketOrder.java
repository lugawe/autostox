package org.autostox.core.domain.trading;

import java.math.BigDecimal;

import org.autostox.core.domain.exception.ValidationException;
import org.autostox.core.domain.instrument.Symbol;

/**
 * Order that is executed at the next available price.
 *
 * @param symbol ticker symbol, e.g. {@code AAPL}
 * @param side whether to buy or sell
 * @param quantity number of units, always positive
 */
public record MarketOrder(Symbol symbol, Side side, BigDecimal quantity) implements Order {

    public MarketOrder {
        if (symbol == null) {
            throw new ValidationException("Symbol must not be null");
        }
        if (side == null) {
            throw new ValidationException("Side must not be null");
        }
        if (quantity == null || quantity.signum() <= 0) {
            throw new ValidationException("Quantity must be positive, but was " + quantity);
        }
    }

    public static MarketOrder buy(Symbol symbol, BigDecimal quantity) {
        return new MarketOrder(symbol, Side.BUY, quantity);
    }

    public static MarketOrder sell(Symbol symbol, BigDecimal quantity) {
        return new MarketOrder(symbol, Side.SELL, quantity);
    }
}
