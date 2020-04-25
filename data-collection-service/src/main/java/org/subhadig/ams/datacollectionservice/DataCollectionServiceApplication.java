package org.subhadig.ams.datacollectionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author subhadig@github
 */
@SpringBootApplication
@EnableAsync
public class DataCollectionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCollectionServiceApplication.class, args);
    }

}
