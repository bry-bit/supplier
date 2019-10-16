package com.guodian.supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.guodian.*")
@EnableTransactionManagement
public class SupplierApplication {

    public static void main(String[] args) {

        SpringApplication.run(SupplierApplication.class, args);
    }

}
