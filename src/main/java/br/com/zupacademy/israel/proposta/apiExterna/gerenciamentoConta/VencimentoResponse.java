package br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Vencimento;

import java.time.LocalDateTime;

public class VencimentoResponse {

    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public VencimentoResponse() {
    }

    public VencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public Vencimento toVencimento() {
        return new Vencimento(id, dia, dataDeCriacao);
    }
}


