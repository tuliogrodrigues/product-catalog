package com.product.catalog.client;

import com.google.common.collect.Lists;
import com.product.catalog.domain.CurrencyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by trodrigues on 13/06/16.
 */
@Component
public class FixerApiClient {

	@Value("${fixer.api.timeout}")
	private Integer apiTimeout;

	@Value("${fixer.api.url}")
	private String fixerUrl;

	private RestTemplate restTemplate;

	public FixerApiClient() {
		restTemplate = new RestTemplate();
	}

	private void configureTimeouts(RestTemplate restTemplate) {
		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(apiTimeout);
		requestFactory.setReadTimeout(apiTimeout);
		restTemplate.setRequestFactory(requestFactory);
	}

	private void configureMessageConverter(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
	}

	private HttpHeaders getHttHeader() {
		HttpHeaders headers = new HttpHeaders();
//		headers.add("X-AUTH-TOKEN", accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
		headers.setAcceptCharset(Lists.newArrayList(Charset.forName("utf-8")));
		return headers;
	}

	public Optional<BigDecimal> getRate(CurrencyType type) {

		HttpHeaders headers = getHttHeader();
		HttpEntity<String> entity = new HttpEntity<>("", headers);

		ResponseEntity<FixerResponse> response = restTemplate.exchange(
				fixerUrl.replace("{base}", type.name()),
				HttpMethod.GET,
				entity,
				FixerResponse.class);

		if( HttpStatus.OK.equals(response.getStatusCode())) {
			return Optional.of(response.getBody().getRate(CurrencyType.EUR));
		} else {
			return Optional.empty();
		}
	}

	private static class FixerResponse implements Serializable {

		private static final long serialVersionUID = -2196783941928243108L;

		private String base;

		private Map<String, BigDecimal>  rates;

		public FixerResponse() {

		}

		public FixerResponse(String base, Map<String, BigDecimal> rates) {
			this.base = base;
			this.rates = rates;
		}

		public String getBase() {
			return base;
		}

		public void setBase(String base) {
			this.base = base;
		}

		public BigDecimal getRate(CurrencyType type) {
			return rates.containsKey(type.name()) ? rates.get(type.name()):BigDecimal.ONE;
		}

		public Map<String, BigDecimal> getRates() {
			return rates;
		}
		public void setRates(Map<String, BigDecimal> rates) {
			this.rates = rates;
		}
	}
}
