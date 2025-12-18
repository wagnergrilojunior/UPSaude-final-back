package com.upsaude.repository.saude_publica.vacina;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.saude_publica.vacina.Vacinas;

public interface VacinasRepository extends JpaRepository<Vacinas, UUID> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);

    boolean existsByCodigoInterno(String codigoInterno);

    boolean existsByCodigoInternoAndIdNot(String codigoInterno, UUID id);
}

