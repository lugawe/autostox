package org.autostox.server.adapters.in.api.json.instrument;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Instrument whose exact type (e.g. stock or ETF) is unknown")
@JsonTypeName("ASSET")
public class JsonAsset extends JsonInstrument {

    public JsonAsset() {}
}
