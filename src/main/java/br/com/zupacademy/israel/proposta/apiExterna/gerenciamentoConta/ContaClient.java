package br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta;

import br.com.zupacademy.israel.proposta.adicionarCarteira.NovaCarteiraRequest;
import br.com.zupacademy.israel.proposta.cadastrarAvisoViagem.AvisoViagemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ContaClient {

    @Value("${url-api-cartoes}")
    private String urlApiCartoes;
    @Value("${server-analise-cartao-not-response}")
    private String messageConnectRefused;

    private final Logger logger = LoggerFactory.getLogger(ContaClient.class);

    public CartaoResponse capturarCartao(String idProposta) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            CartaoResponse cartaoResponse = restTemplate.getForObject(urlApiCartoes + "?idProposta=" + idProposta,
                    CartaoResponse.class);
            return cartaoResponse;
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Erro grave!! Uma proposta NAO_ELEGIVEL não pode ser associada a um cartão.");
        } catch (Exception e) {
            logger.error(messageConnectRefused);
            throw new RuntimeException(messageConnectRefused);
        }
    }

    public boolean notificarSistemaLegadoBloqueioCartao(String userAgent, String idCartao) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(urlApiCartoes + "/" + idCartao + "/bloqueios",
                    Map.of("sistemaResponsavel", userAgent), String.class);
            return true;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return false;
        } catch (Exception e) {
            logger.error(messageConnectRefused);
            return false;
        }
    }

    public boolean notificarSistemaLegadoAvisoViagem(AvisoViagemRequest avisoViagemRequest, String idCartao) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(urlApiCartoes + "/" + idCartao + "/avisos", avisoViagemRequest, String.class);
            return true;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return false;
        } catch (Exception e) {
            logger.error(messageConnectRefused);
            return false;
        }
    }

    public boolean notificarSistemaLegadoCarteiraAdicionada(String idCartao, NovaCarteiraRequest request) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(urlApiCartoes + "/" + idCartao + "/carteiras",
                    Map.of("email", request.getEmail(),
                            "carteira",request.getCarteiraDigital().toString()), String.class);
            return true;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return false;
        } catch (Exception e) {
            logger.error(messageConnectRefused);
            return false;
        }
    }

}
