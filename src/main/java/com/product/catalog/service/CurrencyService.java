package com.product.catalog.service;

import com.product.catalog.client.FixerApiClient;
import com.product.catalog.domain.CurrencyType;
import com.product.catalog.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by trodrigues on 13/06/16.
 */
@Service
public class CurrencyService {

	@Autowired
	private FixerApiClient fixerApiClient;

	public boolean isNotEuroCurrency(Price price) {
		return !CurrencyType.EUR.equals(price.getType());
	}

	public Optional<Price> convertPriceToEuro(Price price) {

		return fixerApiClient.getRate(price.getType())
				.map(rate -> new Price(rate.multiply(price.getValue()), CurrencyType.EUR));
	}
}
