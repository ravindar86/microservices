package com.microservices.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class GatewayServerApplication {

    @GetMapping("/test")
    public String test() {
        return "Testing Gateway";
    }
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @GetMapping("/")
    public Mono<String> home(WebSession webSession) {
        return Mono.just(webSession.getId());
    }

}
