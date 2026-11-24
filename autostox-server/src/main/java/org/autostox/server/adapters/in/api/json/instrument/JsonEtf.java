package org.autostox.server.adapters.in.api.json.instrument;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Exchange traded fund")
@JsonTypeName("ETF")
public class JsonEtf extends JsonInstrument {

    public JsonEtf() {}
}
