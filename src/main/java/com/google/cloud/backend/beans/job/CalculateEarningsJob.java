package com.google.cloud.backend.beans.job;

import com.google.appengine.tools.pipeline.Job3;
import com.google.appengine.tools.pipeline.Value;
import com.google.cloud.backend.beans.Earnings;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

public class CalculateEarningsJob extends Job3<Earnings, LimitOrder, LimitOrder, Wallet> {

	private static final long serialVersionUID = 1L;

	@Override
	public Value<Earnings> run(LimitOrder bidOrder, LimitOrder askOrder, Wallet wallet) throws Exception {
		return immediate(new Earnings(bidOrder, askOrder, wallet.getCurrency()));
	}

}
