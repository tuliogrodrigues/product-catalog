package com.product.catalog.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by trodrigues on 13/06/16.
 */
public class Price implements Serializable {

	private static final long serialVersionUID = -4355047020627079209L;

	@Column(name = "price")
	@NumberFormat(style= NumberFormat.Style.CURRENCY)
	private BigDecimal value;

	@Transient
	@Enumerated(EnumType.STRING)
	private CurrencyType type;

	public Price() {

	}

	public Price(BigDecimal value, CurrencyType type) {
		this.value = value;
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public CurrencyType getType() {
		return type;
	}

	public void setType(CurrencyType type) {
		this.type = type;
	}
}
