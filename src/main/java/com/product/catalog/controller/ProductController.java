package com.product.catalog.controller;

import com.product.catalog.domain.Product;
import com.product.catalog.service.ProductService;
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
@RequestMapping(value = "products", produces = "application/json")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Collection<Product> list() {
		return productService.findAll();
	}

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Product get(@PathVariable String id) {
		return productService.find(id);
	}

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Product save(@Valid @RequestBody Product product) {
		return productService.save(product);
	}

	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Product update(@PathVariable String id, @Valid @RequestBody Product product) {
		return productService.update(id, product);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		productService.remove(id);
	}
}
