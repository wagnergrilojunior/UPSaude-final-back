package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoLeito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoLeitoRepository extends JpaRepository<SigtapProcedimentoLeito, UUID> {
}
