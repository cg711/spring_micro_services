package com.example.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    /**
     * Establishes a gateway connection with both applog and student.
     * @param builder The route locator object used to link each route.
     * @return A route locator object used to find each microservice by their respective routes.
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/student/**")
                        .uri("http://localhost:8081/student-service"))
                .route(r -> r.path("/applog/**")
                        .uri("http://localhost:8082/applog-service"))
                .build();
    }
}
