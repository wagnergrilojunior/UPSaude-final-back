package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoComponenteRede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoComponenteRedeRepository extends JpaRepository<SigtapProcedimentoComponenteRede, UUID> {
}
