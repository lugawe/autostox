package org.autostox.core.domain.strategy;

import org.autostox.core.domain.marketdata.Interval;

/**
 * Trading algorithm that runs unchanged in backtests and live trading.
 */
public interface Strategy {

    Interval interval();

    int lookback();

    void onCandle(StrategyContext context);
}
