package br.com.zupacademy.israel.proposta.bloquearCartao;

import br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta.ContaClient;
import br.com.zupacademy.israel.proposta.criarBiometria.CartaoRepository;
import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import br.com.zupacademy.israel.proposta.exceptions.ApiErroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class BloquearCartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ContaClient contaClient;

    @PutMapping("/{idCartao}/bloquear")
    public ResponseEntity<Void> bloquearCartao(@PathVariable @NotBlank String idCartao,
                                               @RequestHeader("user-agent") @NotBlank String userAgent,
                                               HttpServletRequest request) {
        String ipClient = request.getLocalAddr();
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (!possivelCartao.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (possivelCartao.get().eBloqueado()) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "cartão", "falha ao bloquear o cartão");
        }
        Cartao cartao = possivelCartao.get();
        boolean sistemaNotificado = contaClient.notificarSistemaLegadoBloqueioCartao(userAgent, idCartao);
        if(!sistemaNotificado){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "cartão","não foi possível fazer o bloqueio, tente mais tarde..");
        }
        Bloqueio dadosBloqueio = new Bloqueio(cartao, userAgent, ipClient);
        cartao.bloquear(dadosBloqueio);
        cartaoRepository.save(cartao);
        return ResponseEntity.ok().build();
    }
}
