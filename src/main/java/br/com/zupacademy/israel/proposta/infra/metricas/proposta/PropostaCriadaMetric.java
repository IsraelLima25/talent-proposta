package br.com.zupacademy.israel.proposta.infra.metricas.proposta;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PropostaCriadaMetric {

    private final MeterRegistry meterRegistry;

    public PropostaCriadaMetric(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void contarCriacaoPropostaMetric() {
        Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada");
        contadorDePropostasCriadas.increment();
    }
}
