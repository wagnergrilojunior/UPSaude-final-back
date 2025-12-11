package com.upsaude.repository;

import com.upsaude.entity.ServicosEstabelecimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServicosEstabelecimentoRepository extends JpaRepository<ServicosEstabelecimento, UUID> {

    Page<ServicosEstabelecimento> findByEstabelecimentoIdOrderByNomeAsc(
            UUID estabelecimentoId, Pageable pageable);

    Page<ServicosEstabelecimento> findByEstabelecimentoIdAndNomeContainingIgnoreCaseOrderByNomeAsc(
            UUID estabelecimentoId, String nome, Pageable pageable);

    List<ServicosEstabelecimento> findByEstabelecimentoIdAndCodigoCnes(
            UUID estabelecimentoId, String codigoCnes);

    List<ServicosEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByNomeAsc(
            UUID estabelecimentoId);

    Page<ServicosEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}
