package org.autostox.core.ports.in.instrument;

import java.util.List;

import org.autostox.core.domain.instrument.Instrument;

public interface InstrumentService {

    List<Instrument> findInstruments(String query, long limit);
}
