package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.upsaude.entity.Paciente;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    Optional<Paciente> findByCpf(String cpf);

    Optional<Paciente> findByEmail(String email);

    Optional<Paciente> findByCns(String cns);

    Optional<Paciente> findByRg(String rg);

    @Query(value = "SELECT p FROM Paciente p",
           countQuery = "SELECT COUNT(p) FROM Paciente p")
    Page<PacienteSimplificadoProjection> findAllSimplificado(Pageable pageable);

    @Override
    @EntityGraph(value = "Paciente.completo")
    @NonNull
    Page<Paciente> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = {
        "enderecos",
        "doencas",
        "doencas.doenca",
        "doencas.cidPrincipal",
        "alergias",
        "alergias.alergia",
        "deficiencias",
        "medicacoes",
        "medicacoes.medicacao",
        "medicacoes.medicacao.identificacao",
        "medicacoes.medicacao.fabricanteEntity",
        "medicacoes.cidRelacionado",
        "dadosSociodemograficos",
        "dadosClinicosBasicos",
        "responsavelLegal",
        "lgpdConsentimento",
        "integracaoGov",
        "convenio"
    })
    @Query("SELECT p FROM Paciente p WHERE p.id = :id")
    Optional<Paciente> findByIdCompleto(@org.springframework.data.repository.query.Param("id") UUID id);
}
