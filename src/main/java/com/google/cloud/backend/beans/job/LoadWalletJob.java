package com.google.cloud.backend.beans.job;

import com.google.appengine.tools.pipeline.Job1;
import com.google.appengine.tools.pipeline.Value;
import com.google.cloud.backend.spi.XChangeFactory;
import com.xeiam.xchange.dto.trade.Wallet;

public class LoadWalletJob extends Job1<Wallet, String> {

	private static final long serialVersionUID = 1L;

	@Override
	public Value<Wallet> run(String currency) throws Exception {
		return immediate(XChangeFactory.get(0).getPollingAccountService().getAccountInfo().getWallet(currency));
	}

}
