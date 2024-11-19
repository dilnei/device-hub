package com.device.hub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition
public class DeviceHubApplication {

  public static void main(String[] args) {
    SpringApplication.run(DeviceHubApplication.class, args);
  }

}
