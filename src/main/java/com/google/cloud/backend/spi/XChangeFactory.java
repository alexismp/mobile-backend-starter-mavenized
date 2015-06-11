package com.google.cloud.backend.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.hitbtc.HitbtcExchange;
import com.xeiam.xchange.kraken.KrakenExchange;

public class XChangeFactory {
    // static List<Exchange> EXCHANGES = new ArrayList<Exchange>();
    static Map<Class<? extends Exchange>, Exchange> EXCHANGES = new HashMap<>();

    static {
        Properties properties = loadExchangesConfiguration();
        ExchangeFactory exchangeFactory = new ExchangeFactory();
        Exchange krakenExchange = exchangeFactory.createExchange(KrakenExchange.class.getName());
        krakenExchange.getExchangeSpecification().setApiKey(properties.getProperty("kraken.api.key"));
        krakenExchange.getExchangeSpecification().setSecretKey(properties.getProperty("kraken.api.secret"));
        krakenExchange.getExchangeSpecification().setUserName(properties.getProperty("kraken.api.user"));
        krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
        EXCHANGES.put(KrakenExchange.class, krakenExchange);

        Exchange hitBtcExchange = exchangeFactory.createExchange(HitbtcExchange.class.getName());
        hitBtcExchange.getExchangeSpecification().setApiKey(properties.getProperty("hitbtc.api.key"));
        hitBtcExchange.getExchangeSpecification().setSecretKey(properties.getProperty("hitbtc.api.secret"));
        hitBtcExchange.applySpecification(hitBtcExchange.getExchangeSpecification());
        EXCHANGES.put(HitbtcExchange.class, hitBtcExchange);

    }

    public static List<Exchange> get() {
        List<Exchange> exchangeList = new ArrayList<Exchange>();
        exchangeList.addAll(EXCHANGES.values());
        return exchangeList;
    }

    public static Exchange get(Class<? extends Exchange> clazz) {
        return EXCHANGES.get(clazz);
    }

    public static Exchange get(String clazzName) {
        try {
            return EXCHANGES.get(Class.forName(clazzName));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    private static Properties loadExchangesConfiguration() {
        Properties properties = new Properties();

        return properties;
    }
}
