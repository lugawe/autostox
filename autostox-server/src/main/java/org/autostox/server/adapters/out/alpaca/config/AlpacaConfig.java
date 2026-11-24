package org.autostox.server.adapters.out.alpaca.config;

import io.smallrye.config.ConfigMapping;
import markets.alpaca.client.TradingApiEnvironment;

@ConfigMapping(prefix = "alpaca")
public interface AlpacaConfig {

    String apiKeyId();

    String apiSecretKey();

    String feed();

    TradingApiEnvironment tradingEnvironment();
}
