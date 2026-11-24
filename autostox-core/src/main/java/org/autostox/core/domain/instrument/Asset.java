package org.autostox.core.domain.instrument;

import org.autostox.core.domain.money.Currency;

/**
 * Instrument whose exact type (e.g. stock or ETF) is unknown.
 *
 * @param isin ISIN, or {@code null} if unknown
 * @param symbol ticker symbol, e.g. {@code AAPL}
 * @param name display name, e.g. {@code Apple Inc.}
 * @param exchange primary listing exchange, e.g. {@code NASDAQ}, or {@code null} if unknown
 * @param currency trading currency
 */
public record Asset(Isin isin, Symbol symbol, String name, String exchange, Currency currency) implements Instrument {}
