package com.google.cloud.backend.beans.job;

import java.math.BigDecimal;

import com.google.appengine.tools.pipeline.Job3;
import com.google.appengine.tools.pipeline.Value;
import com.google.cloud.backend.spi.XChangeFactory;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.hitbtc.HitbtcExchange;

public class LoadAskOrderJob extends Job3<LimitOrder, CurrencyPair, LimitOrder, Wallet> {

	private static final long serialVersionUID = 1L;

	@Override
	public Value<LimitOrder> run(CurrencyPair currencyPair, LimitOrder bOrder, Wallet wallet) throws Exception {
		Exchange exchange = XChangeFactory.get(HitbtcExchange.class);
		OrderBook orderBook = exchange.getPollingMarketDataService().getOrderBook(currencyPair, 10);
		for (LimitOrder limitOrder : orderBook.getBids()) {

			if (limitOrder.getTradableAmount().compareTo(bOrder.getTradableAmount()) > 0) {
				if (limitOrder.getLimitPrice().multiply(bOrder.getTradableAmount())
						.compareTo(wallet.getAvailable().multiply(new BigDecimal(0.05))) > 0) {
					return immediate(new LimitOrder(OrderType.ASK, bOrder.getTradableAmount(), currencyPair,
							limitOrder.getId(), limitOrder.getTimestamp(), limitOrder.getLimitPrice()));
				}
			}

		}
		return immediate(null);

		// OrderBook orderBook =
		// exchange.getPollingMarketDataService().getOrderBook(currencyPair,
		// 10);
		// for (LimitOrder limitOrder : orderBook.getAsks()) {
		// if
		// (limitOrder.getTradableAmount().multiply(limitOrder.getLimitPrice()).compareTo(wallet.getAvailable())
		// > 0) {
		// return immediate(new LimitOrder(OrderType.BID,
		// wallet.getAvailable().divide(limitOrder.getLimitPrice(),
		// 10, RoundingMode.HALF_DOWN), currencyPair, limitOrder.getId(),
		// limitOrder.getTimestamp(),
		// limitOrder.getLimitPrice()));
		// }
		// }
		// return immediate(null);
	}

}
