package br.com.zupacademy.israel.proposta.criarNovaProposta;

import br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta.ContaClient;
import br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta.CartaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class AssociarCartaoScheduling {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private ContaClient contaClient;

    @Scheduled(fixedDelayString = "${peridiocidade.executa-operacao}")
    public void associarCartao() {

        List<Proposta> propostas = buscarPropostasElegiveisSemCartaoAssociado();
        if (existePropostasElegiveis(propostas)) {
            for (Proposta proposta : propostas) {
                Assert.isTrue(proposta.getStatus().equals(StatusProposta.ELEGIVEL),
                        "Erro grave!! Uma proposta NAO_ELEGIVEL não pode ser associada a um cartão.");
                CartaoResponse cartaoResponse = contaClient.capturarCartao(proposta.getDocumento());
                proposta.adicionarCartao(cartaoResponse);
                propostaRepository.save(proposta);
            }
        }
    }

    private boolean existePropostasElegiveis(List<Proposta> propostas) {
        return propostas.size() > 0;
    }

    private List<Proposta> buscarPropostasElegiveisSemCartaoAssociado() {
        return propostaRepository.findByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);
    }
}
