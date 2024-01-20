package br.com.zupacademy.israel.proposta.cadastrarAvisoViagem;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AvisoViagem {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @NotBlank
    private String destino;
    @Future
    private LocalDate dataTermino;
    private LocalDateTime instanteCriacao;
    @NotBlank
    private String ipCliente;
    @NotBlank
    private String userAgent;
    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem(){
    }

    public AvisoViagem(@NotBlank String destino, @Future LocalDate dataTermino, @NotBlank String ipCliente,
                       @NotBlank String userAgent, @NotNull Cartao cartao) {
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.instanteCriacao = LocalDateTime.now();
        this.cartao = cartao;
        this.id = UUID.randomUUID();
    }
}
