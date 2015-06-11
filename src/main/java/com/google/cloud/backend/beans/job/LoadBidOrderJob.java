package com.google.cloud.backend.beans.job;

import java.math.RoundingMode;

import com.google.appengine.tools.pipeline.Job2;
import com.google.appengine.tools.pipeline.Value;
import com.google.cloud.backend.spi.XChangeFactory;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.kraken.KrakenExchange;

public class LoadBidOrderJob extends Job2<LimitOrder, CurrencyPair, Wallet> {

	private static final long serialVersionUID = 1L;

	@Override
	public Value<LimitOrder> run(CurrencyPair currencyPair, Wallet wallet) throws Exception {
		Exchange exchange = XChangeFactory.get(KrakenExchange.class);
		OrderBook orderBook = exchange.getPollingMarketDataService().getOrderBook(currencyPair, 10);
		for (LimitOrder limitOrder : orderBook.getAsks()) {
			if (limitOrder.getTradableAmount().multiply(limitOrder.getLimitPrice()).compareTo(wallet.getAvailable()) > 0) {
				return immediate(new LimitOrder(OrderType.BID, wallet.getAvailable().divide(limitOrder.getLimitPrice(),
						10, RoundingMode.HALF_DOWN), currencyPair, limitOrder.getId(), limitOrder.getTimestamp(),
						limitOrder.getLimitPrice()));
			}
		}
		return immediate(null);
	}

}
