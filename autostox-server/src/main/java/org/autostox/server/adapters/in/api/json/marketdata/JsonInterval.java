package org.autostox.server.adapters.in.api.json.marketdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Length of one candle")
public enum JsonInterval {
    MINUTES_1,
    MINUTES_5,
    HOURS_1,
    DAYS_1,
    WEEKS_1
}
