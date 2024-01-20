package br.com.zupacademy.israel.proposta.adicionarCarteira;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Carteira {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @Email
    @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoCarteira carteira;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira(){
    }

    public Carteira(Cartao cartao, String email, TipoCarteira carteira) {
        this.cartao = cartao;
        this.email = email;
        this.carteira = carteira;
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira1 = (Carteira) o;
        return carteira == carteira1.carteira;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carteira);
    }

    public UUID getId() {
        return id;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }
}
