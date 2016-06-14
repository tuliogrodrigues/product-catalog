package com.product.catalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/**
 * Created by trodrigues on 13/06/16.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException implements Supplier<RuntimeException> {

	@Override
	public RuntimeException get() {
		return this;
	}
}
