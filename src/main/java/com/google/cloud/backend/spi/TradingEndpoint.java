package com.google.cloud.backend.spi;

import java.math.BigDecimal;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.pipeline.JobInfo;
import com.google.appengine.tools.pipeline.PipelineService;
import com.google.appengine.tools.pipeline.PipelineServiceFactory;
import com.google.cloud.backend.beans.Earnings;
import com.google.cloud.backend.beans.job.StartTradingJob;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

@Api(name = "tradingbackend", namespace = @ApiNamespace(ownerDomain = "bronko.com", ownerName = "bronko.com", packagePath = "trading"), useDatastoreForAdditionalConfig = AnnotationBoolean.TRUE)
public class TradingEndpoint {

	@ApiMethod(path = "Trade/start", httpMethod = "POST")
	public Earnings start(User user) throws Exception {

		PipelineService service = PipelineServiceFactory.newPipelineService();
		String pipelineHandle = service.startNewPipeline(new StartTradingJob(), CurrencyPair.BTC_EUR.counterSymbol);

		JobInfo jobInfo = service.getJobInfo(pipelineHandle);
		JobInfo.State state = jobInfo.getJobState();
		if (JobInfo.State.COMPLETED_SUCCESSFULLY == state) {
			System.out.println("The output is " + jobInfo.getOutput());
		}

		return new Earnings(new LimitOrder(OrderType.ASK, new BigDecimal(10.0), CurrencyPair.BTC_EUR, "1d", null,
				new BigDecimal(1.0)), new LimitOrder(OrderType.BID, new BigDecimal(1.0), CurrencyPair.BTC_EUR, "1d",
				null, new BigDecimal(1.0)), pipelineHandle);
	}

}
