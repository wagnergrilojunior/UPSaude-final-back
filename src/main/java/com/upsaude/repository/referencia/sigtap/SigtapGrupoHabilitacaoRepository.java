package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapGrupoHabilitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapGrupoHabilitacaoRepository extends JpaRepository<SigtapGrupoHabilitacao, UUID> {
    Optional<SigtapGrupoHabilitacao> findByCodigoOficial(String codigoOficial);
    boolean existsByCodigoOficial(String codigoOficial);
    List<SigtapGrupoHabilitacao> findByCodigoOficialIn(List<String> codigos);
}
