package com.workbook.crane.common.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = {"com.workbook.crane"})
@EnableJpaRepositories(basePackages = {"com.workbook.crane"} )
public class PersistenceConfiguration {

}
