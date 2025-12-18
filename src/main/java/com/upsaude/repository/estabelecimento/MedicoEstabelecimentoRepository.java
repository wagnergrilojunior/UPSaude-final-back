package com.upsaude.repository.estabelecimento;

import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicoEstabelecimentoRepository extends JpaRepository<MedicoEstabelecimento, UUID> {

    Optional<MedicoEstabelecimento> findByMedicoIdAndEstabelecimentoId(UUID medicoId, UUID estabelecimentoId);

    Page<MedicoEstabelecimento> findByMedicoId(UUID medicoId, Pageable pageable);

    Page<MedicoEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<MedicoEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    Page<MedicoEstabelecimento> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    Page<MedicoEstabelecimento> findByTipoVinculoAndEstabelecimentoId(TipoVinculoProfissionalEnum tipoVinculo, UUID estabelecimentoId, Pageable pageable);

    List<MedicoEstabelecimento> findByEstabelecimentoIdAndDataFimIsNull(UUID estabelecimentoId);

    List<MedicoEstabelecimento> findByMedicoIdAndDataFimIsNull(UUID medicoId);

    List<MedicoEstabelecimento> findByEstabelecimentoIdAndDataInicioBetweenOrEstabelecimentoIdAndDataFimBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId2, OffsetDateTime dataInicio2, OffsetDateTime dataFim2);
}
