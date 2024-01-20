package br.com.zupacademy.israel.proposta.adicionarCarteira;

import br.com.zupacademy.israel.proposta.apiExterna.gerenciamentoConta.ContaClient;
import br.com.zupacademy.israel.proposta.criarBiometria.CartaoRepository;
import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import br.com.zupacademy.israel.proposta.exceptions.ApiErroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class AdicionarCarteiraController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ContaClient contaClient;

    @PutMapping("/{idCartao}/carteira")
    public ResponseEntity<Void> adicionarCarteira(@PathVariable String idCartao,
                                                  @Valid @RequestBody NovaCarteiraRequest request,
                                                  UriComponentsBuilder uri) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if(!possivelCartao.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Cartao cartao = possivelCartao.get();
        if(cartao.temCarteira(request.getCarteiraDigital())){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "carteira-digital",
                    "cartão já associado a carteira " + request.getCarteiraDigital().toString());
        }
        boolean sistemaNotificado = contaClient.notificarSistemaLegadoCarteiraAdicionada(idCartao, request);
        if(!sistemaNotificado){
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "carteira",
                    "não foi possível adicionar a carteira neste momento, tente mais tarde..");
        }
        Carteira carteira = request.toModel(cartao);
        cartao.adicionarCarteira(carteira);
        cartaoRepository.save(cartao);
        return ResponseEntity.created(uri.path("/carteiras/{id}")
                .buildAndExpand(carteira.getId()).toUri()).build();
    }
}
