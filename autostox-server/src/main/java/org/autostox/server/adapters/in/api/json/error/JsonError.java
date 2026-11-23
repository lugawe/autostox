package org.autostox.server.adapters.in.api.json.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Error")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonError {

    @Schema(description = "HTTP status code", examples = "400")
    @JsonProperty("status")
    private int status;

    @Schema(description = "Error message", examples = "Limit must be positive, but was 0")
    @JsonProperty("message")
    private String message;

    public JsonError() {}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
