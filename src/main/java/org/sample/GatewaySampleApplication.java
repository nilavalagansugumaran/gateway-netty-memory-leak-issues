package org.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class GatewaySampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySampleApplication.class, args);
    }

}
