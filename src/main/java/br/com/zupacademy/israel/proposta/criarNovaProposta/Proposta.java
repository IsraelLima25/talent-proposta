package br.com.zupacademy.israel.proposta.criarNovaProposta;

import br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro.ResultadoAnalise;
import br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro.StatusRetornoAnaliseFinanceira;
import br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta.CartaoResponse;
import br.com.zupacademy.israel.proposta.validators.CPFOrCNPJ;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @NotBlank
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private StatusProposta status;
    @OneToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank @Email String email,
                    @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.id = UUID.randomUUID();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void adicionarResultadoAnalise(ResultadoAnalise resultadoAnalise) {
        StatusRetornoAnaliseFinanceira resultado = resultadoAnalise
                .getResultadoSolicitacao();
        status = resultado.normaliza();
    }

    public Cartao getCartao() {
        return cartao;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void adicionarCartao(CartaoResponse cartaoResponse){
       this.cartao = cartaoResponse.toModel();
    }
}
