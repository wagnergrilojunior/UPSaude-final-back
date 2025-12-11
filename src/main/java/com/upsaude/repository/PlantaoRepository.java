package com.upsaude.repository;

import com.upsaude.entity.Plantao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoPlantaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, UUID> {

    Page<Plantao> findByProfissionalIdOrderByDataHoraInicioDesc(UUID profissionalId, Pageable pageable);

    Page<Plantao> findByMedicoIdOrderByDataHoraInicioDesc(UUID medicoId, Pageable pageable);

    Page<Plantao> findByEstabelecimentoIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Plantao> findByTipoPlantaoOrderByDataHoraInicioDesc(TipoPlantaoEnum tipoPlantao, Pageable pageable);

    Page<Plantao> findByProfissionalIdAndDataHoraInicioBetweenOrderByDataHoraInicioAsc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Plantao> findByEstabelecimentoIdAndDataHoraInicioBetweenOrderByDataHoraInicioAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    List<Plantao> findByProfissionalIdAndDataHoraInicioLessThanEqualAndDataHoraFimGreaterThanEqual(
            UUID profissionalId, OffsetDateTime agora, OffsetDateTime agora2);

    Page<Plantao> findByTenantOrderByDataHoraInicioDesc(Tenant tenant, Pageable pageable);
}
