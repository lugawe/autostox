package org.autostox.core.ports.out.marketdata;

import java.util.concurrent.Flow;

import org.autostox.core.domain.marketdata.CandleQuery;
import org.autostox.core.domain.marketdata.CandleSeries;
import org.autostox.core.domain.marketdata.Quote;
import org.autostox.core.domain.marketdata.Symbol;

public interface MarketDataProvider {

    CandleSeries getCandles(Symbol symbol, CandleQuery query);

    Quote getQuote(Symbol symbol);

    Flow.Publisher<Quote> streamQuotes(Symbol symbol);
}
