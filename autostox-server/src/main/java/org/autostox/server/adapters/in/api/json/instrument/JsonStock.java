package org.autostox.server.adapters.in.api.json.instrument;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Share of a company")
@JsonTypeName("STOCK")
public class JsonStock extends JsonInstrument {

    public JsonStock() {}
}
