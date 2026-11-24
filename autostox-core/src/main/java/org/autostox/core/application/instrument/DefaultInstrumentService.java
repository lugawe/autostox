package org.autostox.core.application.instrument;

import java.time.Clock;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.autostox.core.domain.instrument.Instrument;
import org.autostox.core.domain.instrument.event.InstrumentsFoundEvent;
import org.autostox.core.ports.in.instrument.InstrumentService;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.instrument.InstrumentProvider;
import org.autostox.core.ports.out.logging.Logger;

public final class DefaultInstrumentService implements InstrumentService {

    private final Clock clock;
    private final Logger logger;
    private final EventPublisher eventPublisher;
    private final InstrumentProvider instrumentProvider;

    public DefaultInstrumentService(
            Clock clock, Logger logger, EventPublisher eventPublisher, InstrumentProvider instrumentProvider) {
        this.clock = Objects.requireNonNull(clock);
        this.logger = Objects.requireNonNull(logger);
        this.eventPublisher = Objects.requireNonNull(eventPublisher);
        this.instrumentProvider = Objects.requireNonNull(instrumentProvider);
    }

    @Override
    public List<Instrument> findInstruments(String query) {

        logger.debug("findInstruments {}", query);

        List<Instrument> instruments = instrumentProvider.findInstruments(query);

        eventPublisher.publish(new InstrumentsFoundEvent(UUID.randomUUID(), clock.instant(), query, instruments));

        return instruments;
    }
}
