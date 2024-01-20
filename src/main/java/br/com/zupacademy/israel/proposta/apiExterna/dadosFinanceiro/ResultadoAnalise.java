package br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro;

import br.com.zupacademy.israel.proposta.validators.CPFOrCNPJ;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ResultadoAnalise {

    @NotBlank
    private String idProposta;
    @CPFOrCNPJ
    @NotBlank
    private String documento;
    @NotBlank
    private String nome;
    @NotNull
    private StatusRetornoAnaliseFinanceira resultadoSolicitacao;

    @Deprecated
    public ResultadoAnalise(){}

    public ResultadoAnalise(@NotBlank String idProposta, @CPFOrCNPJ @NotBlank String documento,
                            @NotBlank String nome, @NotNull StatusRetornoAnaliseFinanceira resultadoSolicitacao) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public StatusRetornoAnaliseFinanceira getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
