package org.autostox.core.ports.out.instrument;

import java.util.List;

import org.autostox.core.domain.instrument.Instrument;

public interface InstrumentProvider {

    List<Instrument> findInstruments(String query);
}
