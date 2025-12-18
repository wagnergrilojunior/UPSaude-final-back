package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapGrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapGrupoRepository extends JpaRepository<SigtapGrupo, UUID> {
    Optional<SigtapGrupo> findByCodigoOficial(String codigoOficial);
    List<SigtapGrupo> findByCodigoOficialIn(List<String> codigos);
}
