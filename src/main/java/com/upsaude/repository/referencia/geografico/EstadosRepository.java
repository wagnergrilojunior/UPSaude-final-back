package com.upsaude.repository.referencia.geografico;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.referencia.geografico.Estados;

public interface EstadosRepository extends JpaRepository<Estados, UUID> {

    Optional<Estados> findByCodigoIbge(String codigoIbge);

    Optional<Estados> findBySigla(String sigla);
}

