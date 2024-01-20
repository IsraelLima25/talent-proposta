package br.com.zupacademy.israel.proposta.cadastrarAvisoViagem;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @Future
    private LocalDate validoAte;

    public AvisoViagemRequest(@NotBlank String destino, @NotNull @Future LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public AvisoViagem toModel(Cartao cartao, String userAgent, HttpServletRequest servletRequest) {
        String ipCliente = servletRequest.getLocalAddr();
        return new AvisoViagem(destino, validoAte, ipCliente, userAgent, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
