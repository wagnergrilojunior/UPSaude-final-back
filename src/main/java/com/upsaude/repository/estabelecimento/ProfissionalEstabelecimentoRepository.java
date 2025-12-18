package com.upsaude.repository.estabelecimento;

import com.upsaude.entity.estabelecimento.ProfissionalEstabelecimento;
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
public interface ProfissionalEstabelecimentoRepository extends JpaRepository<ProfissionalEstabelecimento, UUID> {

    Optional<ProfissionalEstabelecimento> findByProfissionalIdAndEstabelecimentoId(UUID profissionalId, UUID estabelecimentoId);

    Page<ProfissionalEstabelecimento> findByProfissionalId(UUID profissionalId, Pageable pageable);

    Page<ProfissionalEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<ProfissionalEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    Page<ProfissionalEstabelecimento> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    Page<ProfissionalEstabelecimento> findByTipoVinculoAndEstabelecimentoId(TipoVinculoProfissionalEnum tipoVinculo, UUID estabelecimentoId, Pageable pageable);

    List<ProfissionalEstabelecimento> findByEstabelecimentoIdAndDataFimIsNull(UUID estabelecimentoId);

    List<ProfissionalEstabelecimento> findByProfissionalIdAndDataFimIsNull(UUID profissionalId);

    List<ProfissionalEstabelecimento> findByEstabelecimentoIdAndDataInicioBetweenOrEstabelecimentoIdAndDataFimBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId2, OffsetDateTime dataInicio2, OffsetDateTime dataFim2);
}
