package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapFormaOrganizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapFormaOrganizacaoRepository extends JpaRepository<SigtapFormaOrganizacao, UUID> {

    Optional<SigtapFormaOrganizacao> findBySubgrupoCodigoOficialAndCodigoOficial(String codigoSubgrupo, String codigoFormaOrganizacao);

    List<SigtapFormaOrganizacao> findBySubgrupoCodigoOficial(String codigoSubgrupo);

    boolean existsBySubgrupoIdAndCodigoOficial(UUID subgrupoId, String codigoOficial);

    List<SigtapFormaOrganizacao> findBySubgrupoCodigoOficialAndCodigoOficialIn(String codigoSubgrupo, List<String> codigos);

    List<SigtapFormaOrganizacao> findBySubgrupoGrupoCodigoOficialAndSubgrupoCodigoOficialAndCodigoOficialIn(
            String codigoGrupo, String codigoSubgrupo, List<String> codigos);
}

