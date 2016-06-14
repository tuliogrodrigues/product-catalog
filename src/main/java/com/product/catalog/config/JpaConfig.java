package com.product.catalog.config;

import com.product.catalog.ProductCatalogApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by trodrigues on 13/06/16.
 */
@Configuration
@EntityScan(basePackageClasses = ProductCatalogApplication.class)
public class JpaConfig {
}
