package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapSubgrupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapSubgrupoRepository extends JpaRepository<SigtapSubgrupo, UUID> {
    Optional<SigtapSubgrupo> findByGrupoCodigoOficialAndCodigoOficial(String codigoGrupo, String codigoSubgrupo);
    List<SigtapSubgrupo> findByGrupoCodigoOficialAndCodigoOficialIn(String codigoGrupo, List<String> codigos);
    List<SigtapSubgrupo> findByGrupoCodigoOficial(String codigoGrupo);
}
