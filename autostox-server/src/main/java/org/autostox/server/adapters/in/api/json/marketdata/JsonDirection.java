package org.autostox.server.adapters.in.api.json.marketdata;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Price movement of a candle")
public enum JsonDirection {
    UP,
    DOWN,
    FLAT
}
