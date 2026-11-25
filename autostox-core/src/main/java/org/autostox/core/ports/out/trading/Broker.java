package org.autostox.core.ports.out.trading;

import org.autostox.core.domain.account.Account;
import org.autostox.core.domain.trading.Order;

public interface Broker {

    Account getAccount();

    void submitOrder(Order order);
}
