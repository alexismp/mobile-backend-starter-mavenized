package com.google.cloud.backend.beans.job;

import com.google.appengine.tools.pipeline.FutureValue;
import com.google.appengine.tools.pipeline.Job1;
import com.google.appengine.tools.pipeline.Value;
import com.google.cloud.backend.beans.Earnings;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

public class StartTradingJob extends Job1<Earnings, String> {

	private static final long serialVersionUID = 1L;

	@Override
	public Value<Earnings> run(String currency) throws Exception {
		LoadWalletJob loadWalletJob = new LoadWalletJob();
		LoadBidOrderJob loadBidOrderJob = new LoadBidOrderJob();
		LoadAskOrderJob loadAskOrderJob = new LoadAskOrderJob();
		CalculateEarningsJob calculateEarningsJob = new CalculateEarningsJob();

		FutureValue<Wallet> wallet = futureCall(loadWalletJob, immediate(currency));

		FutureValue<LimitOrder> bOrder = futureCall(loadBidOrderJob, immediate(CurrencyPair.BTC_EUR), wallet);
		FutureValue<LimitOrder> aOrder = futureCall(loadAskOrderJob, immediate(CurrencyPair.BTC_EUR), bOrder, wallet);
		return futureCall(calculateEarningsJob, bOrder, aOrder, wallet);

	}

	// @Override
	// public Value<Integer> run(Integer x, Integer y, Integer z) {
	// DiffJob diffJob = new DiffJob();
	// MultJob multJob = new MultJob();
	//
	// FutureValue<Integer> r = futureCall(diffJob, immediate(x), immediate(y));
	// FutureValue<Integer> s = futureCall(diffJob, immediate(x), immediate(z));
	// FutureValue<Integer> t = futureCall(multJob, r, s);
	// FutureValue<Integer> u = futureCall(diffJob, t, immediate(2));
	// return u;
	// }
}