package com.product.catalog.controller;

import com.product.catalog.domain.Category;
import com.product.catalog.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Created by trodrigues on 13/06/16.
 */
@RestController
@RequestMapping(value = "categories", produces = "application/json")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<Category> list() {
		return categoryService.findAll();
	}

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Category get(@PathVariable Long id) {
		return categoryService.find(id);
	}

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Category save(@Valid @RequestBody Category category) {
		return categoryService.save(category);
	}

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Category update(@PathVariable Long id, @Valid @RequestBody Category category) {
		return categoryService.update(id, category);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		categoryService.remove(id);
	}
}
