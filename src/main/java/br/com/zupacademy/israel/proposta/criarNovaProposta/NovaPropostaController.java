package br.com.zupacademy.israel.proposta.criarNovaProposta;

import br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro.AnaliseFinanceiraClient;
import br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro.ResultadoAnalise;
import br.com.zupacademy.israel.proposta.infra.metricas.proposta.PropostaCriadaMetric;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private AnaliseFinanceiraClient analiseFinanceira;
    @Autowired
    private PropostaCriadaMetric meuContador;
    @Autowired
    private Tracer tracer;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public ResponseEntity<Void> criarProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uri) {

        Span activeSpan = tracer.activeSpan();
        Optional<Proposta> possivelPropostaRepetida = propostaRepository.findAll().stream()
                .filter(proposta -> bCryptPasswordEncoder.matches(request.getDocumento(),proposta.getDocumento())).findFirst();
        if (possivelPropostaRepetida.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Proposta proposta = request.toModel(bCryptPasswordEncoder);
        ResultadoAnalise resultadoAnalise = analiseFinanceira.analisar(proposta);
        activeSpan.log("proposta enviada para análise");
        proposta.adicionarResultadoAnalise(resultadoAnalise);
        propostaRepository.save(proposta);
        meuContador.contarCriacaoPropostaMetric();
        activeSpan.setTag("idProposta",proposta.getDocumento());
        return ResponseEntity.created(uri.path("/propostas/{id}")
                .buildAndExpand(proposta.getDocumento()).toUri()).build();
    }
}
