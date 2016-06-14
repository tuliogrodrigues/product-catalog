package com.product.catalog.repository;

import com.product.catalog.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by trodrigues on 13/06/16.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Collection<Category> findAll();

	Optional<Category> findById(Long id);

	Optional<Category> findByName(String name);
}
