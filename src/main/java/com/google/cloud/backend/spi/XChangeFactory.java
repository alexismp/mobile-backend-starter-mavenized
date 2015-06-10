package com.google.cloud.backend.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptsy.CryptsyExchange;
import com.xeiam.xchange.kraken.KrakenExchange;

public class XChangeFactory {
	static List<Exchange> EXCHANGES = new ArrayList<Exchange>();
	static {
		Properties properties = loadExchangesConfiguration();
		ExchangeFactory exchangeFactory = new ExchangeFactory();
		Exchange krakenExchange = exchangeFactory.createExchange(KrakenExchange.class.getName());
		krakenExchange.getExchangeSpecification().setApiKey(properties.getProperty("kraken.api.key"));
		krakenExchange.getExchangeSpecification().setSecretKey(properties.getProperty("kraken.api.secret"));
		krakenExchange.getExchangeSpecification().setUserName(properties.getProperty("kraken.api.user"));
		krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
		EXCHANGES.add(krakenExchange);

		Exchange cryptsyExchange = exchangeFactory.createExchange(CryptsyExchange.class.getName());
		cryptsyExchange.getExchangeSpecification().setApiKey(properties.getProperty("cryptsy.api.key"));
		cryptsyExchange.getExchangeSpecification().setSecretKey(properties.getProperty("cryptsy.api.secret"));
		cryptsyExchange.applySpecification(cryptsyExchange.getExchangeSpecification());
		EXCHANGES.add(cryptsyExchange);
	}

	public static Exchange get(int index) {
		return EXCHANGES.get(index);

	}

	private static Properties loadExchangesConfiguration() {
		Properties properties = new Properties();

		return properties;
	}
}
