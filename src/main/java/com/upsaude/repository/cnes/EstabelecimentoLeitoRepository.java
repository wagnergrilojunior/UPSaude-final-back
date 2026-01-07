package com.upsaude.repository.cnes;

import com.upsaude.entity.cnes.EstabelecimentoLeito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstabelecimentoLeitoRepository extends JpaRepository<EstabelecimentoLeito, UUID> {

    Optional<EstabelecimentoLeito> findByEstabelecimentoIdAndTipoLeitoCodigo(UUID estabelecimentoId,
            String tipoLeitoCodigo);

    Page<EstabelecimentoLeito> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);
}
