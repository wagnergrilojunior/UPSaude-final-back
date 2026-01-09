package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapProcedimentoDetalheRepository extends JpaRepository<SigtapProcedimentoDetalhe, UUID> {
    
    Optional<SigtapProcedimentoDetalhe> findByProcedimentoId(UUID procedimentoId);
}
