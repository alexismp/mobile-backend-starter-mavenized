package com.google.cloud.backend.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.xeiam.xchange.dto.trade.LimitOrder;

public class Earnings implements Serializable {

	private static final long serialVersionUID = 1L;

	BigDecimal earnings;
	BigDecimal earningsPercent;
	String currency;

	public Earnings(LimitOrder bidOrder, LimitOrder askOrder, String currency) {
		super();
		this.earnings = askOrder.getLimitPrice().multiply(askOrder.getTradableAmount())
				.subtract(bidOrder.getLimitPrice().multiply(bidOrder.getTradableAmount()));
		this.earningsPercent = earnings.divide(bidOrder.getLimitPrice().multiply(bidOrder.getTradableAmount()), 5,
				RoundingMode.HALF_DOWN);
		this.currency = currency;
	}

	public BigDecimal getEarnings() {
		return earnings;
	}

	public BigDecimal getEarningsPercent() {
		return earningsPercent;
	}

	public String getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "Earnings [earnings=" + earnings + ", earningsPercent=" + earningsPercent + ", currency=" + currency
				+ "]";
	}
}
