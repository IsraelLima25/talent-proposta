package br.com.zupacademy.israel.proposta.criarNovaProposta;

import br.com.zupacademy.israel.proposta.adicionarCarteira.Carteira;
import br.com.zupacademy.israel.proposta.adicionarCarteira.TipoCarteira;
import br.com.zupacademy.israel.proposta.bloquearCartao.Bloqueio;
import br.com.zupacademy.israel.proposta.criarBiometria.Biometria;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    private String numero;
    @NotNull
    private LocalDateTime emitidoEm;
    @NotBlank
    private String titular;
    @Positive
    @NotNull
    private BigDecimal limite;
    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    private Vencimento vencimento;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Biometria> biometrias = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Bloqueio> bloqueios = new HashSet<>();
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Carteira> carteiras = new HashSet<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String numero, @NotNull LocalDateTime emitidoEm, @NotBlank String titular,
                  @Positive @NotNull BigDecimal limite, @NotNull Vencimento vencimento) {
        this.numero = numero;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.statusCartao = StatusCartao.DESBLOQUEADO;
    }

    public String getNumero() {
        return numero;
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

    public Vencimento getVencimento() {
        return vencimento;
    }

    public void adicionarBiometria(Biometria biometria) {
        Assert.notNull(biometria, "a biometria não deve ser inválida");
        this.biometrias.add(biometria);
    }

    public boolean eBloqueado() {
        return statusCartao.equals(StatusCartao.BLOQUEADO);
    }

    public void bloquear(Bloqueio bloqueio) {
        Assert.isTrue(!eBloqueado(), "um cartão não pode ser bloqueado 2 vezes");
        this.statusCartao = StatusCartao.BLOQUEADO;
        this.bloqueios.add(bloqueio);
    }

    public void adicionarCarteira(Carteira carteira) {
        Assert.notNull(carteira, "carteira não deve ser null");
        carteiras.add(carteira);
    }

    public boolean temCarteira(TipoCarteira tipoCarteira) {
        return this.carteiras.stream().filter(carteira -> carteira.getCarteira().equals(tipoCarteira))
                .count() > 0;
    }
}
