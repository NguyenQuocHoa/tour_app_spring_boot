package com.database;

import com.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    // logger
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
//            Product productA = new Product(1L, "Macbook pro 1", 2020, 2400.0, "");
//            Product productB = new Product(2L, "Macbook mini 2", 2021, 3500.0, "");
//            logger.info("insert data" + productRepository.save(productA));
//            logger.info("insert data" + productRepository.save(productB));
        };
    }
}
