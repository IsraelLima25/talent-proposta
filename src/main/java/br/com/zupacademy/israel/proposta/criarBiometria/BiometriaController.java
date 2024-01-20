package br.com.zupacademy.israel.proposta.criarBiometria;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/biometrias")
public class BiometriaController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @PutMapping("/{idCartao}")
    public ResponseEntity<Void> adicionarBiometria(@PathVariable @NotBlank String idCartao,
                                               @Valid @RequestBody BiometriaRequest request,
                                               UriComponentsBuilder uri) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if(!possivelCartao.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cartao cartao = possivelCartao.get();
        Biometria biometria = request.toModel(cartao);
        cartao.adicionarBiometria(biometria);
        cartaoRepository.save(cartao);
        return ResponseEntity.created(uri.path("/biometrias/{id}")
                .buildAndExpand(biometria.getId()).toUri()).build();
    }
}
