package br.com.zupacademy.israel.proposta.validators;

import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64 {
    String message() default "{finger-print-invalido}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
