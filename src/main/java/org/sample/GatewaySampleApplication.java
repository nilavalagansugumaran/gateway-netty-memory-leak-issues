package org.sample;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration
public class GatewaySampleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewaySampleApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }

}
