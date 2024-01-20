package br.com.zupacademy.israel.proposta.criarNovaProposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropostaRepository extends JpaRepository<Proposta, UUID> {

   List<Proposta> findByStatusAndCartaoIsNull(StatusProposta status);

   Optional<Proposta> findByDocumento(String documento);

}
