package com.upsaude.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Habilita agendamentos (@Scheduled).
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
}

