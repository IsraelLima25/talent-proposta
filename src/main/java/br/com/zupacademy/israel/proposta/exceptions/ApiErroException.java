package br.com.zupacademy.israel.proposta.exceptions;

import org.springframework.http.HttpStatus;

public class ApiErroException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String campo;
    private final String causa;

    public ApiErroException(HttpStatus httpStatus, String campo, String causa) {
        this.httpStatus = httpStatus;
        this.campo = campo;
        this.causa = causa;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCampo() {
        return campo;
    }

    public String getCausa() {
        return causa;
    }
}
