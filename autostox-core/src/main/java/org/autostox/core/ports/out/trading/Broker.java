package org.autostox.core.ports.out.trading;

import java.util.concurrent.Flow;

import org.autostox.core.domain.account.Account;
import org.autostox.core.domain.trading.Fill;
import org.autostox.core.domain.trading.Order;

public interface Broker {

    Account getAccount();

    Flow.Publisher<Fill> fills();

    void submitOrder(Order order);
}
