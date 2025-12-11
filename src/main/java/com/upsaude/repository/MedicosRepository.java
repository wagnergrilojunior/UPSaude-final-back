package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.upsaude.entity.Medicos;
import com.upsaude.entity.Tenant;

public interface MedicosRepository extends JpaRepository<Medicos, UUID> {

    Page<Medicos> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT m FROM Medicos m WHERE m.registroProfissional.crm = :crm " +
           "AND m.registroProfissional.crmUf = :crmUf AND m.tenant = :tenant")
    Optional<Medicos> findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenant(
            @Param("crm") String crm,
            @Param("crmUf") String crmUf,
            @Param("tenant") Tenant tenant);

    @Query("SELECT m FROM Medicos m WHERE m.dadosPessoais.cpf = :cpf AND m.tenant = :tenant")
    Optional<Medicos> findByDadosPessoaisCpfAndTenant(
            @Param("cpf") String cpf,
            @Param("tenant") Tenant tenant);

    @Override
    @EntityGraph(attributePaths = {
        "especialidades"
    })
    @NonNull
    Page<Medicos> findAll(@NonNull Pageable pageable);
}
