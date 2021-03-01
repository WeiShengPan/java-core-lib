package com.pws.spring.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * path: /management/health
 *
 * @author wilson.pan
 * @date 2020/11/30
 */
@Component
public class RandomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        double chance = ThreadLocalRandom.current().nextDouble();
        builder.up();
        if (chance > 0.9) {
            builder.down();
        }
    }
}
