package br.com.zupacademy.israel.proposta.criarBiometria;

import br.com.zupacademy.israel.proposta.criarNovaProposta.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
