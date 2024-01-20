package br.com.zupacademy.israel.proposta.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public List<CampoInvalido> handlerArgumentNotValid(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<CampoInvalido> camposInvalidos = extrairErros(fieldErrors);
        return camposInvalidos;
    }

    @ExceptionHandler(ApiErroException.class)
    public ResponseEntity<CampoInvalido> handleApiErroException(ApiErroException apiErroException) {
        CampoInvalido campoInvalido = new CampoInvalido(apiErroException.getCampo(), apiErroException.getCausa());
        return ResponseEntity.status(apiErroException.getHttpStatus()).body(campoInvalido);
    }

    private List<CampoInvalido> extrairErros(List<FieldError> fieldErrors) {
        List<CampoInvalido> camposInvalido = new ArrayList<>();
        fieldErrors.forEach(error -> {
            CampoInvalido fieldErro = new CampoInvalido(error.getField(),
                    messageSource.getMessage(error, LocaleContextHolder.getLocale()));
            camposInvalido.add(fieldErro);
        });
        return camposInvalido;
    }
}
