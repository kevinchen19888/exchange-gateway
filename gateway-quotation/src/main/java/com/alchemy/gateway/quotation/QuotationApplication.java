package com.alchemy.gateway.quotation;

import com.alchemy.gateway.market.MarketConfig;
import com.alchemy.gateway.quotation.config.QuotationRabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Import(value = {QuotationRabbitConfig.class, MarketConfig.class})
@EnableJpaRepositories(basePackages = "com.alchemy.gateway.quotation.repository")
@EntityScan(basePackages = "com.alchemy.gateway.quotation.entity")
public class QuotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotationApplication.class, args);
    }

}
