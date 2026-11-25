package org.autostox.core.domain.trading;

import java.math.BigDecimal;

import org.autostox.core.domain.instrument.Symbol;

/**
 * Instruction to buy or sell an instrument.
 */
public sealed interface Order permits MarketOrder {

    /**
     * @return ticker symbol, e.g. {@code AAPL}
     */
    Symbol symbol();

    /**
     * @return whether to buy or sell
     */
    Side side();

    /**
     * @return number of units, always positive
     */
    BigDecimal quantity();
}
