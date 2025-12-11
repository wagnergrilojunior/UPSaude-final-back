package com.upsaude.repository;

import com.upsaude.entity.Falta;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoFaltaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface FaltaRepository extends JpaRepository<Falta, UUID> {

    Page<Falta> findByProfissionalIdOrderByDataFaltaDesc(UUID profissionalId, Pageable pageable);

    Page<Falta> findByMedicoIdOrderByDataFaltaDesc(UUID medicoId, Pageable pageable);

    Page<Falta> findByEstabelecimentoIdOrderByDataFaltaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Falta> findByTipoFaltaOrderByDataFaltaDesc(TipoFaltaEnum tipoFalta, Pageable pageable);

    Page<Falta> findByProfissionalIdAndDataFaltaBetweenOrderByDataFaltaDesc(
            UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<Falta> findByEstabelecimentoIdAndDataFaltaBetweenOrderByDataFaltaDesc(
            UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<Falta> findByProfissionalIdAndTipoFaltaOrderByDataFaltaDesc(
            UUID profissionalId, TipoFaltaEnum tipoFalta, Pageable pageable);

    Page<Falta> findByTenantOrderByDataFaltaDesc(Tenant tenant, Pageable pageable);
}
