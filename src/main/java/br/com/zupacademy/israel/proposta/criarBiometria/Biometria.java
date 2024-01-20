package br.com.zupacademy.israel.proposta.criarBiometria;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import br.com.zupacademy.israel.proposta.validators.Base64;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @NotBlank
    @Base64
    private String fingerPrint;
    private LocalDateTime dataCriacao;
    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria(){
    }

    public Biometria(@NotBlank String fingerPrint, @NotNull Cartao cartao) {
        this.id = UUID.randomUUID();
        this.fingerPrint = fingerPrint;
        this.dataCriacao = LocalDateTime.now();
        this.cartao = cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria = (Biometria) o;
        return fingerPrint.equals(biometria.fingerPrint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fingerPrint);
    }

    public UUID getId() {
        return id;
    }
}
