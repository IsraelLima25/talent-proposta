package br.com.zupacademy.israel.proposta.adicionarCarteira;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCarteiraRequest {

    @NotBlank
    @Email
    private String email;
    @NotNull
    private TipoCarteira tipoCarteira;

    public NovaCarteiraRequest(@NotBlank @Email String email, @NotNull TipoCarteira tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(cartao,email, tipoCarteira);
    }

    public TipoCarteira getCarteiraDigital() {
        return tipoCarteira;
    }

    public String getEmail() {
        return email;
    }
}
