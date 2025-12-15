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

import com.upsaude.entity.Paciente;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    Optional<Paciente> findByCpf(String cpf);

    Optional<Paciente> findByEmail(String email);

    Optional<Paciente> findByCns(String cns);

    Optional<Paciente> findByRg(String rg);

    @Query(value = "SELECT * FROM pacientes WHERE cpf = :cpf AND (:tenantId IS NULL OR :tenantId IS NOT NULL) LIMIT 1", nativeQuery = true)
    Optional<Paciente> findByCpfAndTenantId(@Param("cpf") String cpf, @Param("tenantId") UUID tenantId);

    @Query(value = "SELECT * FROM pacientes WHERE email = :email AND (:tenantId IS NULL OR :tenantId IS NOT NULL) LIMIT 1", nativeQuery = true)
    Optional<Paciente> findByEmailAndTenantId(@Param("email") String email, @Param("tenantId") UUID tenantId);

    @Query(value = "SELECT * FROM pacientes WHERE cns = :cns AND (:tenantId IS NULL OR :tenantId IS NOT NULL) LIMIT 1", nativeQuery = true)
    Optional<Paciente> findByCnsAndTenantId(@Param("cns") String cns, @Param("tenantId") UUID tenantId);

    @Query(value = "SELECT * FROM pacientes WHERE rg = :rg AND (:tenantId IS NULL OR :tenantId IS NOT NULL) LIMIT 1", nativeQuery = true)
    Optional<Paciente> findByRgAndTenantId(@Param("rg") String rg, @Param("tenantId") UUID tenantId);

    @Query(value = "SELECT p FROM Paciente p",
           countQuery = "SELECT COUNT(p) FROM Paciente p")
    Page<PacienteSimplificadoProjection> findAllSimplificado(Pageable pageable);

    @Override
    @EntityGraph(value = "Paciente.completo")
    @NonNull
    Page<Paciente> findAll(@NonNull Pageable pageable);

    // Listagem sem relacionamentos (evita MultipleBagFetchException)
    @Query("SELECT p FROM Paciente p")
    Page<Paciente> findAllSemRelacionamentos(Pageable pageable);

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
    Optional<Paciente> findByIdCompleto(@Param("id") UUID id);

    @Query(value = "SELECT * FROM pacientes WHERE id = :id AND (:tenantId IS NULL OR :tenantId IS NOT NULL)", nativeQuery = true)
    Optional<Paciente> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query(value = "SELECT * FROM pacientes WHERE id = :id AND (:tenantId IS NULL OR :tenantId IS NOT NULL)", nativeQuery = true)
    Optional<Paciente> findByIdCompletoAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query(value = "SELECT * FROM pacientes WHERE (:tenantId IS NULL OR :tenantId IS NOT NULL)",
           countQuery = "SELECT COUNT(*) FROM pacientes",
           nativeQuery = true)
    Page<Paciente> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query(value = "SELECT p.id, p.criado_em as createdAt, p.atualizado_em as updatedAt, p.ativo as active, " +
           "p.nome_completo as nomeCompleto, p.cpf, p.rg, p.cns, p.data_nascimento as dataNascimento, " +
           "p.sexo, p.estado_civil as estadoCivil, p.telefone, p.email, p.nome_mae as nomeMae, " +
           "p.nome_pai as nomePai, p.responsavel_nome as responsavelNome, p.responsavel_cpf as responsavelCpf, " +
           "p.responsavel_telefone as responsavelTelefone, p.numero_carteirinha as numeroCarteirinha, " +
           "p.data_validade_carteirinha as dataValidadeCarteirinha, p.observacoes, p.raca_cor as racaCor, " +
           "p.nacionalidade, p.pais_nascimento as paisNascimento, p.naturalidade, " +
           "p.municipio_nascimento_ibge as municipioNascimentoIbge, p.escolaridade, " +
           "p.ocupacao_profissao as ocupacaoProfissao, p.situacao_rua as situacaoRua, " +
           "p.status_paciente as statusPaciente, p.data_obito as dataObito, " +
           "p.causa_obito_cid10 as causaObitoCid10, p.cartao_sus_ativo as cartaoSusAtivo, " +
           "p.data_atualizacao_cns as dataAtualizacaoCns, p.tipo_atendimento_preferencial as tipoAtendimentoPreferencial, " +
           "p.origem_cadastro as origemCadastro, p.nome_social as nomeSocial, " +
           "p.identidade_genero as identidadeGenero, p.orientacao_sexual as orientacaoSexual, " +
           "p.possui_deficiencia as possuiDeficiencia, p.tipo_deficiencia as tipoDeficiencia, " +
           "p.cns_validado as cnsValidado, p.tipo_cns as tipoCns, " +
           "p.acompanhado_por_equipe_esf as acompanhadoPorEquipeEsf " +
           "FROM pacientes p WHERE (:tenantId IS NULL OR :tenantId IS NOT NULL)",
           countQuery = "SELECT COUNT(*) FROM pacientes",
           nativeQuery = true)
    Page<PacienteSimplificadoProjection> findAllSimplificadoByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);
}
