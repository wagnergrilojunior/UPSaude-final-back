package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapServicoClassificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapServicoClassificacaoRepository extends JpaRepository<SigtapServicoClassificacao, UUID> {
}
