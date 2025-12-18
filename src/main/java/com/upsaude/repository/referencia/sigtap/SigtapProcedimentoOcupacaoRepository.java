package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOcupacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoOcupacaoRepository extends JpaRepository<SigtapProcedimentoOcupacao, UUID> {
}
