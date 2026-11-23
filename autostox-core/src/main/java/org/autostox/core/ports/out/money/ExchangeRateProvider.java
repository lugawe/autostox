package org.autostox.core.ports.out.money;

import java.math.BigDecimal;

import org.autostox.core.domain.money.Currency;

public interface ExchangeRateProvider {

    /** Returns how many units of {@code to} one unit of {@code from} is worth. */
    BigDecimal getExchangeRate(Currency from, Currency to);
}
