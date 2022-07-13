package com.example.gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter authenticationFilter;

    /**
     * Establishes a gateway connection with applog, security and student.
     * Authenticates using a security middleware.
     * @param builder The route locator object used to link each route.
     * @return A route locator object used to find each microservice by their respective routes.
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/student/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/applog/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8082"))
                .route(r -> r.path("/security/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8084"))
                .build();
    }
}
