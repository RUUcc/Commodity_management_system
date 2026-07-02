package org.example.commodity_management_system_jzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CommodityManagementSystemJzsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommodityManagementSystemJzsApplication.class, args);
    }

}
