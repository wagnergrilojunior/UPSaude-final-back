package com.upsaude.repository;

import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoricoHabilitacaoProfissionalRepository extends JpaRepository<HistoricoHabilitacaoProfissional, UUID> {

    Page<HistoricoHabilitacaoProfissional> findByProfissionalIdOrderByDataEventoDesc(UUID profissionalId, Pageable pageable);

    Page<HistoricoHabilitacaoProfissional> findByProfissionalIdAndTipoEventoOrderByDataEventoDesc(
            UUID profissionalId, String tipoEvento, Pageable pageable);

    List<HistoricoHabilitacaoProfissional> findByProfissionalIdAndDataEventoBetweenOrderByDataEventoDesc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim);
}
