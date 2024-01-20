package br.com.zupacademy.israel.proposta.acompanhamentoProposta;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Proposta;
import br.com.zupacademy.israel.proposta.criarNovaProposta.PropostaRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/acompanhamentoPropostas")
public class AcompanhamentoPropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/{idProposta}")
    public ResponseEntity<PropostaResponse> buscarDadosProposta(@PathVariable String idProposta) {

        Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta");
        PropostaResponse response = timerConsultarProposta.record(() -> {
            Optional<Proposta> possivelProposta = propostaRepository.findById(UUID.fromString(idProposta));
            if (!possivelProposta.isPresent()) {
                ResponseEntity.notFound().build();
            }
            PropostaResponse propostaResponse = new PropostaResponse(possivelProposta.get());
            return propostaResponse;
        });
        return ResponseEntity.ok(response);
    }
}
