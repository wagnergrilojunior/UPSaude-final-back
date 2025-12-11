package com.upsaude.repository;

import com.upsaude.entity.ControlePonto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ControlePontoRepository extends JpaRepository<ControlePonto, UUID> {

    Page<ControlePonto> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);

    Page<ControlePonto> findByMedicoIdOrderByDataHoraDesc(UUID medicoId, Pageable pageable);

    Page<ControlePonto> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ControlePonto> findByProfissionalIdAndDataPontoOrderByDataHoraAsc(UUID profissionalId, LocalDate dataPonto, Pageable pageable);

    Page<ControlePonto> findByProfissionalIdAndDataPontoBetweenOrderByDataHoraAsc(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<ControlePonto> findByEstabelecimentoIdAndDataPontoBetweenOrderByDataHoraAsc(UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
}
