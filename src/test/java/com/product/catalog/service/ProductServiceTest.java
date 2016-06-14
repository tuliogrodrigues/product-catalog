package com.product.catalog.service;

import com.product.catalog.ProductCatalogApplication;
import com.product.catalog.domain.Category;
import com.product.catalog.domain.CurrencyType;
import com.product.catalog.domain.Price;
import com.product.catalog.domain.Product;
import com.product.catalog.exceptions.ConflictException;
import com.product.catalog.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by trodrigues on 13/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ProductCatalogApplication.class)
@WebAppConfiguration
@Transactional
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Test(expected = NotFoundException.class)
	public void shouldNotFoundAProduct() {
		productService.find("1");
	}

	@Test
	public void shouldFindProductAfterPersisted() {

		Category category = new Category();
		category.setName("Mobile");
		category = categoryService.save(category);

		Product product = new Product();
		product.setName("Iphone");
		product.setBrand("Apple");
		product.setDescription("Iphone 32GB");
		product.setPrice(new Price(BigDecimal.ONE, CurrencyType.EUR));
		product.setQuantity(10);
		product.setCategory(category);

		product = productService.save(product);

		Product productDB = productService.find(product.getId());

		Assert.assertNotNull(productDB);
		Assert.assertEquals(productDB, product);
	}

	@Test(expected = ConflictException.class)
	public void shouldNotSaveSameEntityTwice() {

		Category category = new Category();
		category.setName("Mobile");
		category = categoryService.save(category);

		Product product = new Product();
		product.setName("Iphone");
		product.setBrand("Apple");
		product.setDescription("Iphone 32GB");
		product.setPrice(new Price(BigDecimal.ONE, CurrencyType.EUR));
		product.setQuantity(10);
		product.setCategory(category);

		productService.save(product);
		productService.save(product);
	}
}
