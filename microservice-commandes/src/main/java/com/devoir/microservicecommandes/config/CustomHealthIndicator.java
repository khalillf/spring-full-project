package com.devoir.microservicecommandes.config;

import com.devoir.microservicecommandes.repositories.CommandeRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-configs")
@RefreshScope
public class CustomHealthIndicator implements HealthIndicator {

    private final CommandeRepository commandeRepository;

    public CustomHealthIndicator(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public Health health() {
        long count = commandeRepository.count();
        if (count > 0) {
            // Il y a au moins une commande => UP
            return Health.up()
                    .withDetail("message", "Il y a " + count + " commande(s) dans la base.")
                    .build();
        } else {
            // Aucune commande => DOWN
            return Health.down()
                    .withDetail("message", "Aucune commande dans la base.")
                    .build();
        }
    }
}