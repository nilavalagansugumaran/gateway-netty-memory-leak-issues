package org.sample;

import org.sample.filters.conditional.SampleResponseBodyHandlerGatewayFilterFactory;
import org.sample.function.ReadFormDataFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
public class GatewaySampleApplication {

    @Autowired
    private ReadFormDataFunction readFormDataFunction;

    @Autowired private GatewayProperties gatewayProperties;

    public static void main(String[] args) {
//        new SpringApplicationBuilder(GatewaySampleApplication.class)
//                .web(WebApplicationType.REACTIVE)
//                .run(args);
        SpringApplication.run(GatewaySampleApplication.class, args);
    }

    @Bean
    public SampleResponseBodyHandlerGatewayFilterFactory.Config config() {
        SampleResponseBodyHandlerGatewayFilterFactory.Config config =  new SampleResponseBodyHandlerGatewayFilterFactory.Config();
        config.setInClass(String.class);
        config.setOutClass(String.class);
        config.setRewriteFunction(readFormDataFunction);
        return config;
    }
}
