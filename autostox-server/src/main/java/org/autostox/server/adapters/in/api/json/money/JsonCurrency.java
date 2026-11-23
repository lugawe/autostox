package org.autostox.server.adapters.in.api.json.money;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "ISO 4217 currency code")
public enum JsonCurrency {
    USD,
    EUR,
    JPY,
    GBP,
    CNY,
    AUD,
    CAD,
    CHF,
    HKD,
    SGD,
    SEK,
    KRW
}
