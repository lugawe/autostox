package org.autostox.core.ports.in.money;

import java.math.BigDecimal;

import org.autostox.core.domain.money.Currency;

public interface ExchangeRateService {

    /** Returns how many units of {@code to} one unit of {@code from} is worth. */
    BigDecimal getExchangeRate(Currency from, Currency to);
}
