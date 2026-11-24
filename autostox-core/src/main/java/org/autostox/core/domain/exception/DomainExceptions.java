package org.autostox.core.domain.exception;

import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.money.Currency;

public final class DomainExceptions {

    private DomainExceptions() {}

    public static NotFoundException createSymbolNotFound(Symbol symbol) {
        return new NotFoundException("Symbol not found: " + symbol);
    }

    public static NotFoundException createCandlesNotFound(Symbol symbol, CandleQuery query) {
        String period;
        if (query.start() != null && query.end() != null) {
            period = " between " + query.start() + " and " + query.end();
        } else if (query.start() != null) {
            period = " from " + query.start();
        } else if (query.end() != null) {
            period = " until " + query.end();
        } else {
            period = "";
        }
        return new NotFoundException("No candles found for " + symbol + period);
    }

    public static NotFoundException createExchangeRateNotFound(Currency from, Currency to) {
        return new NotFoundException("Exchange rate not found: " + from + "/" + to);
    }
}
