package br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class CartaoResponse {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Set<BloqueioResponse> bloqueios;
    private Set<AvisoResponse> avisos;
    private Set<CarteiraResponse> carteiras;
    private Set<ParcelaResponse> parcelas;
    private BigDecimal limite;
    private RenegociacaoResponse renegociacao;
    private VencimentoResponse vencimento;
    private String idProposta;

    public Cartao toModel(){
        return new Cartao(id,emitidoEm,titular,limite,vencimento.toVencimento());
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}
