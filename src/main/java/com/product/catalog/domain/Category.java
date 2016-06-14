package com.product.catalog.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by trodrigues on 13/06/16.
 */
@Entity
@Table(name = "categories")
public class Category implements Serializable {

	private static final long serialVersionUID = 3371846754257490220L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Category parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		return name.equals(category.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
