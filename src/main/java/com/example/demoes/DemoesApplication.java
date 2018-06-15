package com.example.demoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class DemoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoesApplication.class, args);
    }
}
