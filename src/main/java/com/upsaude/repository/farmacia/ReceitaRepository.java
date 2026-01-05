package com.upsaude.repository.farmacia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.upsaude.entity.farmacia.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, UUID> {

    @Query("SELECT r FROM Receita r WHERE r.paciente.id = :pacienteId")
    Page<Receita> findByPacienteId(@Param("pacienteId") UUID pacienteId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {
            "paciente",
            "consulta",
            "medico",
            "itens",
            "itens.sigtapProcedimento"
    })
    @NonNull
    Optional<Receita> findById(@NonNull UUID id);

    @EntityGraph(attributePaths = {
            "paciente",
            "consulta",
            "medico",
            "itens",
            "itens.sigtapProcedimento",
            "itens.dispensacoes"
    })
    @Query("SELECT r FROM Receita r WHERE r.id = :id")
    Optional<Receita> findByIdCompleto(@Param("id") UUID id);

    @Query("SELECT r FROM Receita r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<Receita> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);
}

