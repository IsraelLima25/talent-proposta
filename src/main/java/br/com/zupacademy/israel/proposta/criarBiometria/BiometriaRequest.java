package br.com.zupacademy.israel.proposta.criarBiometria;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BiometriaRequest {

    @NotBlank
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$",
            message = "{finger-print-invalido}")
    private String fingerPrint;

    /**
     * @param fingerPrint jackson não serializa no construtor um único parametro foi necessário criar setter.
     */
    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(fingerPrint,cartao);
    }
}
