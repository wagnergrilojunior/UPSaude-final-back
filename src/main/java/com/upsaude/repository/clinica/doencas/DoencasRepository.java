package com.upsaude.repository.clinica.doencas;

import com.upsaude.entity.clinica.doencas.Doencas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoencasRepository extends JpaRepository<Doencas, UUID> {

    Optional<Doencas> findByNome(String nome);

    Page<Doencas> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Doencas> findByCronica(Boolean cronica, Pageable pageable);

    @Query("SELECT d FROM Doencas d WHERE d.cidPrincipal.codigo = :codigoCid")
    Page<Doencas> findByCodigoCid(@Param("codigoCid") String codigoCid, Pageable pageable);

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);

    boolean existsByCodigoInterno(String codigoInterno);

    boolean existsByCodigoInternoAndIdNot(String codigoInterno, UUID id);
}
