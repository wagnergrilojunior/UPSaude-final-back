package com.upsaude.repository;

import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    Page<Atendimento> findByPacienteIdOrderByInformacoesDataHoraDesc(UUID pacienteId, Pageable pageable);

    Page<Atendimento> findByProfissionalIdOrderByInformacoesDataHoraDesc(UUID profissionalId, Pageable pageable);

    Page<Atendimento> findByEstabelecimentoIdOrderByInformacoesDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Atendimento> findByTenantOrderByInformacoesDataHoraDesc(Tenant tenant, Pageable pageable);

    Page<Atendimento> findByEstabelecimentoIdAndTenantOrderByInformacoesDataHoraDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
