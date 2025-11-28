package com.upsaude.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade para armazenar informações de integração com sistemas governamentais.
 * Relacionamento 1:1 com Paciente.
 * Utilizada para integração com SUS/e-SUS/SISAB/RNDS e outros sistemas governamentais.
 */
@Entity
@Table(name = "integracao_gov", schema = "public",
       indexes = {
           @Index(name = "idx_integracao_gov_paciente", columnList = "paciente_id"),
           @Index(name = "idx_integracao_gov_uuid_rnds", columnList = "uuid_rnds"),
           @Index(name = "idx_integracao_gov_id_integracao", columnList = "id_integracao_gov")
       })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGov extends BaseEntity {

    /**
     * Relacionamento 1:1 com Paciente.
     * O paciente possui um único registro de integração governamental.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    /**
     * UUID do paciente no RNDS (Registro Nacional de Dados de Saúde).
     * Identificador único no sistema nacional de saúde brasileiro.
     */
    @Column(name = "uuid_rnds")
    private UUID uuidRnds;

    /**
     * ID de integração com sistemas governamentais.
     * Pode ser usado para integração com e-SUS, SISAB, ou outros sistemas.
     */
    @Size(max = 100, message = "ID de integração deve ter no máximo 100 caracteres")
    @Column(name = "id_integracao_gov", length = 100)
    private String idIntegracaoGov;

    /**
     * Data e hora da última sincronização com sistemas governamentais.
     * Utilizado para controle de sincronização e identificação de dados desatualizados.
     */
    @Column(name = "data_sincronizacao_gov")
    private LocalDateTime dataSincronizacaoGov;

    /**
     * Código INE (Identificador Nacional de Equipe) da equipe de saúde.
     * Utilizado na Estratégia de Saúde da Família (ESF).
     */
    @Size(max = 10, message = "INE da equipe deve ter no máximo 10 caracteres")
    @Column(name = "ine_equipe", length = 10)
    private String ineEquipe;

    /**
     * Microárea de cobertura da equipe de saúde.
     * Utilizado para organização territorial da atenção básica.
     */
    @Size(max = 10, message = "Microárea deve ter no máximo 10 caracteres")
    @Column(name = "microarea", length = 10)
    private String microarea;

    /**
     * Código CNES (Cadastro Nacional de Estabelecimentos de Saúde) do estabelecimento de origem.
     * Identifica o estabelecimento onde o paciente foi cadastrado inicialmente.
     */
    @Size(max = 7, message = "CNES do estabelecimento deve ter no máximo 7 caracteres")
    @Column(name = "cnes_estabelecimento_origem", length = 7)
    private String cnesEstabelecimentoOrigem;

    /**
     * Origem do cadastro do paciente.
     * Indica de onde veio o cadastro (ex: "e-SUS", "SISAB", "Cadastro Manual", etc.).
     */
    @Size(max = 30, message = "Origem do cadastro deve ter no máximo 30 caracteres")
    @Column(name = "origem_cadastro", length = 30)
    private String origemCadastro;
}

