package br.com.zupacademy.israel.proposta.infra.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class APIContasHealthCheck implements HealthIndicator {

    @Override
    public Health health() {

        Map<String, Object> details = new HashMap<>();
        details.put("descrição", "API de contas");
        details.put("endereço", "http://localhost:8888");
        if (check()) {
            return Health.status(Status.UP).withDetails(details).build();
        } else {
            return Health.status(Status.DOWN).withDetails(details).build();
        }
    }

    private boolean check() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject("http://localhost:8888/swagger-ui/index.html", String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
