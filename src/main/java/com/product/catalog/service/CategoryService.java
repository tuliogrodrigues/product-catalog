package com.product.catalog.service;

import com.product.catalog.domain.Category;
import com.product.catalog.exceptions.ConflictException;
import com.product.catalog.exceptions.NotFoundException;
import com.product.catalog.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by trodrigues on 13/06/16.
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public Collection<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category find(Long id) {
		return categoryRepository
				.findById(id)
				.orElseThrow(new NotFoundException());
	}

	public Category save(Category category) {

		if(categoryRepository.findByName(category.getName()).isPresent()) {
			throw new ConflictException();
		}
		return  categoryRepository.save(category);
	}

	public Category update(Long id, Category category) {

		return categoryRepository
				.findById(id)
				.map(c -> {
					BeanUtils.copyProperties(category, c, "id");
					return categoryRepository.save(c);
				}).orElseThrow(new NotFoundException());
	}

	public void remove(Long id) {
		categoryRepository
				.findById(id)
				.ifPresent(categoryRepository::delete);
	}
}
