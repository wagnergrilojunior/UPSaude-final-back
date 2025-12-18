package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoModalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoModalidadeRepository extends JpaRepository<SigtapProcedimentoModalidade, UUID> {
}
