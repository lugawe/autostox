package org.autostox.core.domain.account;

import java.math.BigDecimal;

import org.autostox.core.domain.exception.ValidationException;

/**
 * Snapshot of a trading account: available cash and open positions.
 *
 * @param cash cash available for buying, never negative
 * @param positions open positions
 */
public record Account(BigDecimal cash, Positions positions) {

    public Account {
        if (cash == null || cash.signum() < 0) {
            throw new ValidationException("Cash must not be negative, but was " + cash);
        }
        if (positions == null) {
            throw new ValidationException("Positions must not be null");
        }
    }
}
