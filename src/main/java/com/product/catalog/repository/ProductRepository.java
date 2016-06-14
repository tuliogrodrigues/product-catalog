package com.product.catalog.repository;

import com.product.catalog.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by trodrigues on 13/06/16.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

	Collection<Product> findAll();

	Optional<Product> findById(String id);

	Collection<Product> findByName(String name);
}
