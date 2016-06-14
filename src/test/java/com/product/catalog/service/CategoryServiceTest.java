package com.product.catalog.service;

import com.product.catalog.ProductCatalogApplication;
import com.product.catalog.domain.Category;
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

/**
 * Created by trodrigues on 13/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ProductCatalogApplication.class)
@WebAppConfiguration
@Transactional
public class CategoryServiceTest {

	@Autowired
	private CategoryService categoryService;

	@Test(expected = NotFoundException.class)
	public void shouldNotFoundACategory() {
		categoryService.find(1L);
	}

	@Test
	public void shouldFindCategoryAfterPersisted() {

		Category category = new Category();
		category.setName("Mobile");
		category = categoryService.save(category);

		Category categoryDB = categoryService.find(category.getId());

		Assert.assertNotNull(categoryDB);
		Assert.assertEquals(categoryDB, category);
	}

	@Test(expected = ConflictException.class)
	public void shouldNotSaveSameEntityTwice() {

		Category category = new Category();
		category.setName("Mobile");
		categoryService.save(category);
		categoryService.save(category);
	}
}
