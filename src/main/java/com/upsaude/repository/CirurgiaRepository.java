package com.upsaude.repository;

import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusCirurgiaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface CirurgiaRepository extends JpaRepository<Cirurgia, UUID> {

    Page<Cirurgia> findByPacienteIdOrderByDataHoraPrevistaDesc(UUID pacienteId, Pageable pageable);

    Page<Cirurgia> findByCirurgiaoPrincipalIdOrderByDataHoraPrevistaAsc(UUID cirurgiaoPrincipalId, Pageable pageable);

    Page<Cirurgia> findByEstabelecimentoIdOrderByDataHoraPrevistaAsc(UUID estabelecimentoId, Pageable pageable);

    Page<Cirurgia> findByStatusOrderByDataHoraPrevistaAsc(StatusCirurgiaEnum status, Pageable pageable);

    Page<Cirurgia> findByCirurgiaoPrincipalIdAndDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            UUID cirurgiaoPrincipalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Cirurgia> findByEstabelecimentoIdAndDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Cirurgia> findByCirurgiaoPrincipalIdAndStatusOrderByDataHoraPrevistaAsc(
            UUID cirurgiaoPrincipalId, StatusCirurgiaEnum status, Pageable pageable);

    Page<Cirurgia> findByDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Cirurgia> findByTenantOrderByDataHoraPrevistaDesc(Tenant tenant, Pageable pageable);
}
