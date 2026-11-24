package org.autostox.server.cdi.core;

import java.time.Clock;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.autostox.core.application.instrument.DefaultInstrumentService;
import org.autostox.core.ports.in.instrument.InstrumentService;
import org.autostox.core.ports.out.event.EventPublisher;
import org.autostox.core.ports.out.instrument.InstrumentProvider;
import org.autostox.core.ports.out.logging.Logger;
import org.autostox.server.adapters.out.logging.Slf4jLoggerFactory;

@ApplicationScoped
public class InstrumentProducer {

    @Inject
    private EventPublisher eventPublisher;

    @Inject
    private InstrumentProvider instrumentProvider;

    public InstrumentProducer() {}

    @Produces
    public InstrumentService produceInstrumentService() {

        Logger logger = Slf4jLoggerFactory.getLogger(DefaultInstrumentService.class);

        return new DefaultInstrumentService(Clock.systemUTC(), logger, eventPublisher, instrumentProvider);
    }
}
