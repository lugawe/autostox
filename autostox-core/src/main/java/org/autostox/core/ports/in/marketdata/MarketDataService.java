package org.autostox.core.ports.in.marketdata;

import java.util.concurrent.Flow;

import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Quote;

public interface MarketDataService {

    CandleSeries getCandles(Symbol symbol, CandleQuery query);

    Quote getQuote(Symbol symbol);

    Flow.Publisher<Quote> streamQuotes(Symbol symbol);
}
