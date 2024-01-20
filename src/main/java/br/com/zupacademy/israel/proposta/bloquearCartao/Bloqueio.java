package br.com.zupacademy.israel.proposta.bloquearCartao;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private Cartao cartao;
    @NotBlank
    private String userAgent;
    @NotBlank
    private String ipClient;
    @NotNull
    private LocalDateTime dataBloqueio;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusBloqueio status;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(Cartao cartao, String userAgent, String ipClient) {
        this.cartao = cartao;
        this.userAgent = userAgent;
        this.ipClient = ipClient;
        this.dataBloqueio = LocalDateTime.now();
        this.status = StatusBloqueio.ATIVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloqueio bloqueio = (Bloqueio) o;
        return cartao.equals(bloqueio.cartao) && userAgent.equals(bloqueio.userAgent) && ipClient.equals(bloqueio.ipClient) && dataBloqueio.equals(bloqueio.dataBloqueio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartao, userAgent, ipClient, dataBloqueio);
    }
}
