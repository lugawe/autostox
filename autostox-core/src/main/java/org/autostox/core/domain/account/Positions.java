package org.autostox.core.domain.account;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.autostox.core.domain.instrument.Symbol;

/**
 * Open positions, at most one per symbol.
 *
 * @param bySymbol positions by symbol
 */
public record Positions(Map<Symbol, Position> bySymbol) {

    public Positions {
        bySymbol = bySymbol == null ? Map.of() : Map.copyOf(bySymbol);
    }

    public Optional<Position> get(Symbol symbol) {
        return Optional.ofNullable(bySymbol.get(symbol));
    }

    public BigDecimal quantity(Symbol symbol) {
        return get(symbol).map(Position::quantity).orElse(BigDecimal.ZERO);
    }
}
