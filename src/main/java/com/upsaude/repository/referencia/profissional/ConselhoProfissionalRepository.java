package com.upsaude.repository.referencia.profissional;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.referencia.profissional.ConselhoProfissional;

public interface ConselhoProfissionalRepository extends JpaRepository<ConselhoProfissional, UUID> {

    @Query("SELECT c FROM ConselhoProfissional c WHERE c.codigo = :codigo")
    Optional<ConselhoProfissional> findByCodigo(@Param("codigo") String codigo);

    @Query("SELECT c FROM ConselhoProfissional c WHERE c.sigla = :sigla AND (c.uf = :uf OR c.uf IS NULL)")
    Optional<ConselhoProfissional> findBySiglaAndUf(@Param("sigla") String sigla, @Param("uf") String uf);

    @Query("SELECT c FROM ConselhoProfissional c WHERE c.sigla = :sigla")
    java.util.List<ConselhoProfissional> findBySigla(@Param("sigla") String sigla);
}
