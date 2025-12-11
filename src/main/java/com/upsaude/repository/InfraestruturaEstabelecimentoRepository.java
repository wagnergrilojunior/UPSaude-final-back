package com.upsaude.repository;

import com.upsaude.entity.InfraestruturaEstabelecimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InfraestruturaEstabelecimentoRepository extends JpaRepository<InfraestruturaEstabelecimento, UUID> {

    Page<InfraestruturaEstabelecimento> findByEstabelecimentoIdOrderByTipoAsc(
            UUID estabelecimentoId, Pageable pageable);

    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndTipo(
            UUID estabelecimentoId, String tipo);

    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndCodigoCnes(
            UUID estabelecimentoId, String codigoCnes);

    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByTipoAsc(
            UUID estabelecimentoId);

    Page<InfraestruturaEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}
