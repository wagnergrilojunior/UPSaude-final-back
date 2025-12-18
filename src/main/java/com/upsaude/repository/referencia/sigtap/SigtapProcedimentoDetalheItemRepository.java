package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoDetalheItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapProcedimentoDetalheItemRepository extends JpaRepository<SigtapProcedimentoDetalheItem, UUID> {
}
