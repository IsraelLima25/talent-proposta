package br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AnaliseFinanceiraClient {

    private final Logger logger = LoggerFactory.getLogger(AnaliseFinanceiraClient.class);

    @Value("${url-api-solicitacao-analise}")
    private String urlApiAnaliseFinanceira;
    @Value("${server-analise-credito-not-response}")
    private String messageConnectRefused;

    public ResultadoAnalise analisar(Proposta proposta) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResultadoAnalise resultadoAnaliseSemRestricao = restTemplate
                    .postForObject(urlApiAnaliseFinanceira + "/solicitacao",
                            Map.of("documento", proposta.getDocumento(), "nome", proposta.getNome(),
                                    "idProposta", proposta.getDocumento()), ResultadoAnalise.class);

            return resultadoAnaliseSemRestricao;
        } catch (HttpClientErrorException e) {
            ResultadoAnalise resultadoAnaliseComRestricao = toJsonAnaliseRestrita(e.getResponseBodyAsString());
            return resultadoAnaliseComRestricao;
        }
        catch (Exception e) {
            logger.error(messageConnectRefused);
            throw  new RuntimeException(messageConnectRefused);
        }
    }

    private ResultadoAnalise toJsonAnaliseRestrita(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ResultadoAnalise resultadoAnaliseJSON = objectMapper.readValue(value, ResultadoAnalise.class);
            return resultadoAnaliseJSON;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
