package org.autostox.core.domain.instrument;

import org.autostox.core.domain.money.Currency;

/**
 * A tradable financial instrument.
 */
public sealed interface Instrument permits Stock, Etf {

    Isin isin();

    Symbol symbol();

    String name();

    String exchange();

    Currency currency();
}
