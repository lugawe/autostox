package org.autostox.server.adapters.out.instrument;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.autostox.core.domain.instrument.Instrument;
import org.autostox.core.ports.out.instrument.InstrumentProvider;
import org.autostox.server.adapters.out.alpaca.AlpacaClient;

@ApplicationScoped
public class AlpacaInstrumentProvider implements InstrumentProvider {

    private final AlpacaClient client;

    @Inject
    public AlpacaInstrumentProvider(AlpacaClient client) {
        this.client = client;
    }

    @Override
    public List<Instrument> findInstruments(String query, long limit) {

        return client.getInstruments().stream()
                .filter(instrument -> instrument.symbol().value().contains(query)
                        || instrument.name().contains(query))
                .limit(limit)
                .toList();
    }
}
