package com.google.cloud.backend.spi;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.utils.Assert;

public class ExchangeFactory {


	/**
	 * Constructor
	 */
	public ExchangeFactory() {

	}

	/**
	 * Create an Exchange object.
	 * <p>
	 * The factory is parameterised with the name of the exchange implementation
	 * class. This must be a class extending {@link com.xeiam.xchange.Exchange}.
	 * </p>
	 * 
	 * @param exchangeClassName
	 *            the fully-qualified class name of the exchange
	 * @return a new exchange instance configured with the default
	 *         {@link com.xeiam.xchange.ExchangeSpecification}
	 */
	public Exchange createExchange(String exchangeClassName) {

		Assert.notNull(exchangeClassName, "exchangeClassName cannot be null");


		// Attempt to create an instance of the exchange provider
		try {

			// Attempt to locate the exchange provider on the classpath
			Class exchangeProviderClass = Class.forName(exchangeClassName);

			// Test that the class implements Exchange
			if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
				// Instantiate through the default constructor and use the
				// default exchange specification
				Exchange exchange = (Exchange) exchangeProviderClass.newInstance();
				exchange.applySpecification(exchange.getDefaultExchangeSpecification());
				return exchange;
			} else {
				throw new ExchangeException("Class '" + exchangeClassName + "' does not implement Exchange");
			}
		} catch (ClassNotFoundException e) {
			throw new ExchangeException("Problem creating Exchange (class not found)", e);
		} catch (InstantiationException e) {
			throw new ExchangeException("Problem creating Exchange (instantiation)", e);
		} catch (IllegalAccessException e) {
			throw new ExchangeException("Problem creating Exchange (illegal access)", e);
		}

		// Cannot be here due to exceptions

	}

	public Exchange createExchange(ExchangeSpecification exchangeSpecification) {

		Assert.notNull(exchangeSpecification, "exchangeSpecfication cannot be null");

		String exchangeClassName = exchangeSpecification.getExchangeClassName();

		// Attempt to create an instance of the exchange provider
		try {

			// Attempt to locate the exchange provider on the classpath
			Class exchangeProviderClass = Class.forName(exchangeClassName);

			// Test that the class implements Exchange
			if (Exchange.class.isAssignableFrom(exchangeProviderClass)) {
				// Instantiate through the default constructor
				Exchange exchange = (Exchange) exchangeProviderClass.newInstance();
				exchange.applySpecification(exchangeSpecification);
				return exchange;
			} else {
				throw new ExchangeException("Class '" + exchangeClassName + "' does not implement Exchange");
			}
		} catch (ClassNotFoundException e) {
			throw new ExchangeException("Problem starting exchange provider (class not found)", e);
		} catch (InstantiationException e) {
			throw new ExchangeException("Problem starting exchange provider (instantiation)", e);
		} catch (IllegalAccessException e) {
			throw new ExchangeException("Problem starting exchange provider (illegal access)", e);
		}

		// Cannot be here due to exceptions

	}

}
