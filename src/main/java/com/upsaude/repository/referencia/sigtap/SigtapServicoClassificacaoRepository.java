package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapServico;
import com.upsaude.entity.referencia.sigtap.SigtapServicoClassificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapServicoClassificacaoRepository extends JpaRepository<SigtapServicoClassificacao, UUID> {
    Optional<SigtapServicoClassificacao> findByServicoAndCodigoClassificacaoAndCompetenciaInicial(
            SigtapServico servico, String codigoClassificacao, String competenciaInicial);
}
