package com.product.catalog.service;

import com.product.catalog.domain.Product;
import com.product.catalog.exceptions.ConflictException;
import com.product.catalog.exceptions.NotFoundException;
import com.product.catalog.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by trodrigues on 13/06/16.
 */
@Service
public class ProductService {

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private ProductRepository productRepository;

	public Collection<Product> findAll() {
		return productRepository.findAll();
	}

	public Product find(String id) {
		return productRepository
				.findById(id)
				.orElseThrow(new NotFoundException());
	}

	public Product save(Product product) {

		if(isSameProduct(product)) {
			throw new ConflictException();
		}

		if(currencyService.isNotEuroCurrency(product.getPrice())) {
			currencyService
					.convertPriceToEuro(product.getPrice())
					.ifPresent(product::setPrice);
		}

		return productRepository.save(product);
	}

	public Product update(String id, Product product) {
		return productRepository
				.findById(id)
				.map(p -> {
					BeanUtils.copyProperties(product, p, "id");
					return productRepository.save(p);
				}).orElseThrow(new NotFoundException());
	}

	public void remove(String id) {
		productRepository
				.findById(id)
				.ifPresent(productRepository::delete);
	}

	private boolean isSameProduct(Product product) {
		return productRepository
				.findByName(product.getName())
				.contains(product);

	}
}
