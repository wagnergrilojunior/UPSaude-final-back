package com.upsaude.repository.paciente;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.upsaude.entity.paciente.Paciente;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

       // Métodos findByCpf, findByEmail, findByCns, findByRg removidos
       // Esses campos foram movidos para PacienteIdentificador e PacienteContato
       // Use os repositories específicos: PacienteIdentificadorRepository e
       // PacienteContatoRepository

       @Query(value = "SELECT p FROM Paciente p", countQuery = "SELECT COUNT(p) FROM Paciente p")
       Page<PacienteSimplificadoProjection> findAllSimplificado(Pageable pageable);

       @Override
       @EntityGraph(value = "Paciente.listagem")
       @NonNull
       Page<Paciente> findAll(@NonNull Pageable pageable);

       // Listagem sem relacionamentos (evita MultipleBagFetchException)
       @Query("SELECT p FROM Paciente p")
       Page<Paciente> findAllSemRelacionamentos(Pageable pageable);

       @EntityGraph(attributePaths = {
                     "enderecos",
                     "deficiencias",
                     "alergias",
                     "dadosSociodemograficos",
                     "dadosClinicosBasicos",
                     "responsavelLegal",
                     "lgpdConsentimento",
                     "integracoesGov",
                     "convenio"
       })
       @Query("SELECT p FROM Paciente p WHERE p.id = :id")
       Optional<Paciente> findByIdCompleto(@Param("id") UUID id);

       @Query(value = "SELECT * FROM pacientes WHERE id = :id AND (:tenantId IS NULL OR :tenantId IS NOT NULL)", nativeQuery = true)
       Optional<Paciente> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

       @EntityGraph(value = "Paciente.prontuarioCompleto")
       @Query("SELECT p FROM Paciente p WHERE p.id = :id AND (:tenantId = :tenantId OR :tenantId IS NULL)")
       Optional<Paciente> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

       @Query(value = "SELECT * FROM pacientes WHERE (:tenantId IS NULL OR :tenantId IS NOT NULL)", countQuery = "SELECT COUNT(*) FROM pacientes", nativeQuery = true)
       Page<Paciente> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

       // Query simplificada atualizada - campos movidos para outras tabelas foram
       // removidos
       @Query(value = "SELECT p.id, p.criado_em as createdAt, p.atualizado_em as updatedAt, p.ativo as active, " +
                     "p.nome_completo as nomeCompleto, p.data_nascimento as dataNascimento, " +
                     "p.sexo, p.numero_carteirinha as numeroCarteirinha, " +
                     "p.data_validade_carteirinha as dataValidadeCarteirinha, p.observacoes, " +
                     "p.status_paciente as statusPaciente, p.tipo_atendimento_preferencial as tipoAtendimentoPreferencial, "
                     +
                     "p.nome_social as nomeSocial " +
                     "FROM pacientes p WHERE (:tenantId IS NULL OR :tenantId IS NOT NULL)", countQuery = "SELECT COUNT(*) FROM pacientes", nativeQuery = true)
       Page<PacienteSimplificadoProjection> findAllSimplificadoByTenant(@Param("tenantId") UUID tenantId,
                     Pageable pageable);
}
