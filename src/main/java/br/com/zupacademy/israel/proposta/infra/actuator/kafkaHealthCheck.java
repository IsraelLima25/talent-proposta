package br.com.zupacademy.israel.proposta.infra.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class kafkaHealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        //TODO check kafka!!
        Map<String, Object> details = new HashMap<>();
        details.put("descrição", "Acesso kafka");
        details.put("endereço", "http://localhost:9092");
        return Health.status(Status.UP).withDetails(details).build();
    }
}
