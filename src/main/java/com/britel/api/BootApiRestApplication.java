package com.britel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Jhonny Vargas.
 */

@SpringBootApplication
public class BootApiRestApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(BootApiRestApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BootApiRestApplication.class);
  }
}