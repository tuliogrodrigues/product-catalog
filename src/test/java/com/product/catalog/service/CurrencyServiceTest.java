package com.product.catalog.service;

import com.product.catalog.ProductCatalogApplication;
import com.product.catalog.domain.CurrencyType;
import com.product.catalog.domain.Price;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

/**
 * Created by trodrigues on 13/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ProductCatalogApplication.class)
@WebAppConfiguration
public class CurrencyServiceTest {

	@Autowired
	private CurrencyService currencyService;

	public void shouldBeBRLDifferentThanEUR() {
		Price realPrice = new Price(new BigDecimal(1.0), CurrencyType.BRL);
		Assert.assertFalse(currencyService.isNotEuroCurrency(realPrice));
	}

	@Test
	public void shouldConvertToEuro() {

		Price realPrice = new Price(new BigDecimal(1.0), CurrencyType.BRL);
		Price euroPrice = currencyService.convertPriceToEuro(realPrice).get();

		Assert.assertNotNull(euroPrice);
		Assert.assertTrue(euroPrice.getValue().compareTo(realPrice.getValue()) < 0);
		Assert.assertTrue(euroPrice.getValue().compareTo(realPrice.getValue()) < 0);
	}

	@Test
	public void shouldReturnSameValue() {

		Price price = new Price(new BigDecimal(1.0), CurrencyType.EUR);
		Price euroPrice = currencyService.convertPriceToEuro(price).get();

		Assert.assertNotNull(euroPrice);
		Assert.assertTrue(euroPrice.getValue().compareTo(price.getValue()) == 0);
	}
}
