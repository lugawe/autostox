package org.autostox.core.domain.instrument;

import org.autostox.core.domain.money.Currency;

/**
 * Exchange traded fund.
 *
 * @param isin ISIN, or {@code null} if unknown
 * @param symbol ticker symbol, e.g. {@code SPY}
 * @param name fund name, e.g. {@code SPDR S&P 500 ETF Trust}
 * @param exchange primary listing exchange, e.g. {@code NYSE ARCA}, or {@code null} if unknown
 * @param currency trading currency
 */
public record Etf(Isin isin, Symbol symbol, String name, String exchange, Currency currency) implements Instrument {}
