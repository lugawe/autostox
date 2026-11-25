package org.autostox.core.domain.strategy;

import java.util.List;

import org.autostox.core.domain.account.Account;
import org.autostox.core.domain.instrument.Symbol;
import org.autostox.core.domain.marketdata.Candle;
import org.autostox.core.domain.trading.Order;

/**
 * Everything a {@link Strategy} may see and do when it is called. Identical in backtests and live trading.
 */
public interface StrategyContext {

    Account account();

    Symbol symbol();

    List<Candle> history();

    void submitOrder(Order order);
}
