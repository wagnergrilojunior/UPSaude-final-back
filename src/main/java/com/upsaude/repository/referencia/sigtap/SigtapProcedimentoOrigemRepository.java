package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOrigem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoOrigemRepository extends JpaRepository<SigtapProcedimentoOrigem, UUID> {
}
