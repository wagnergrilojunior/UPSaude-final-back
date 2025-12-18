package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoIncremento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoIncrementoRepository extends JpaRepository<SigtapProcedimentoIncremento, UUID> {
}
