package com.product.catalog.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by trodrigues on 13/06/16.
 */

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 3562325005984058818L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@NotNull
	private String name;

	private String description;

	@Embedded
	private Price price;

	@NumberFormat(style= Style.NUMBER, pattern = "#####")
	private Integer quantity;

	private String brand;

	@Pattern(regexp = "|(^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])", message = "invalid URL")
	private String image;

	@DateTimeFormat(style="S-")
	private Date createdAt;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
	private Category category;

	@PrePersist
	private void prePersist() {
		createdAt = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Product product = (Product) o;

		if (!name.equals(product.name)) return false;
		if (!price.equals(product.price)) return false;
		if (brand != null ? !brand.equals(product.brand) : product.brand != null) return false;
		return category.equals(product.category);

	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + price.hashCode();
		result = 31 * result + (brand != null ? brand.hashCode() : 0);
		result = 31 * result + category.hashCode();
		return result;
	}
}
