package br.com.zupacademy.israel.proposta.cadastrarAvisoViagem;

import br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta.ContaClient;
import br.com.zupacademy.israel.proposta.criarBiometria.CartaoRepository;
import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import br.com.zupacademy.israel.proposta.exceptions.ApiErroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/viagens")
public class CadastrarAvisoViagemController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private AvisoViagemRepository avisoViagemRepository;
    @Autowired
    private ContaClient contaClient;

    @PostMapping("/{idCartao}/aviso")
    public ResponseEntity<Void> cadastrarAvisoViagem(@PathVariable @NotBlank String idCartao,
                                                     @Valid @RequestBody AvisoViagemRequest request,
                                                     @RequestHeader("user-agent") String userAgent,
                                                     HttpServletRequest servletRequest) {

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (!possivelCartao.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        boolean sistemaNotificado = contaClient.notificarSistemaLegadoAvisoViagem(request, idCartao);
        if(!sistemaNotificado) {
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "viagem","não foi possível adicionar a viagem, tente mais tarde..");
        }
        AvisoViagem avisoViagem = request.toModel(possivelCartao.get(), userAgent, servletRequest);
        avisoViagemRepository.save(avisoViagem);
        return ResponseEntity.ok().build();
    }



}
