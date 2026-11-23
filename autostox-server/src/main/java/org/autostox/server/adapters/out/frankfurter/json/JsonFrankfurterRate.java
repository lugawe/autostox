package org.autostox.server.adapters.out.frankfurter.json;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JsonFrankfurterRate(BigDecimal rate) {}
