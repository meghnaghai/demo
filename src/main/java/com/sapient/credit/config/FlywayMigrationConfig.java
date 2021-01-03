package com.sapient.credit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FlywayMigrationConfig {

  @Bean
  public FlywayMigrationStrategy cleanMigrateStrategy() {
    log.info("Calling cleanMigrateStrategy");
    return flyway -> {
      log.info("Calling flyway -> repair");
      flyway.repair();
      log.info("Calling flyway -> migrate");
      flyway.migrate();
      log.info("End flyway -> repair and migrate");
    };
  }
}
