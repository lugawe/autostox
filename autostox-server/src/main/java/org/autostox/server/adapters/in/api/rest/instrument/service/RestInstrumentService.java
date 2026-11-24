package org.autostox.server.adapters.in.api.rest.instrument.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.instrument.Instrument;
import org.autostox.core.ports.in.instrument.InstrumentService;
import org.autostox.server.adapters.in.api.json.converter.JsonConverter;
import org.autostox.server.adapters.in.api.json.instrument.JsonInstrument;

@ApplicationScoped
public class RestInstrumentService {

    private final JsonConverter jsonConverter;
    private final InstrumentService instrumentService;

    @Inject
    public RestInstrumentService(JsonConverter jsonConverter, InstrumentService instrumentService) {
        this.jsonConverter = jsonConverter;
        this.instrumentService = instrumentService;
    }

    public List<JsonInstrument> findInstruments(String query, long limit) {

        List<Instrument> result = instrumentService.findInstruments(query, limit);

        return result.stream().map(jsonConverter::toJsonInstrument).toList();
    }
}
