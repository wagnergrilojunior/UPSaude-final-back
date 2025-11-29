package com.upsaude.entity;

import com.upsaude.entity.embeddable.CalendarioVacinal;
import com.upsaude.entity.embeddable.ComposicaoVacina;
import com.upsaude.entity.embeddable.ConservacaoVacina;
import com.upsaude.entity.embeddable.ContraindicacoesVacina;
import com.upsaude.entity.embeddable.EficaciaVacina;
import com.upsaude.entity.embeddable.EsquemaVacinal;
import com.upsaude.entity.embeddable.IdadeAplicacaoVacina;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalVacina;
import com.upsaude.entity.embeddable.ReacoesAdversasVacina;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVacinaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import com.upsaude.util.converter.TipoVacinaEnumConverter;
import com.upsaude.util.converter.UnidadeMedidaEnumConverter;
import com.upsaude.util.converter.ViaAdministracaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um catálogo de vacinas.
 * Armazena informações completas sobre vacinas para sistemas de gestão de saúde.
 * Baseado em padrões do PNI (Programa Nacional de Imunizações) e ANVISA.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "vacinas", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_vacina_codigo_pni", columnNames = {"codigo_pni"}),
           @UniqueConstraint(name = "uk_vacina_codigo_sus", columnNames = {"codigo_sus"})
       },
       indexes = {
           @Index(name = "idx_vacina_nome", columnList = "nome"),
           @Index(name = "idx_vacina_tipo", columnList = "tipo"),
           @Index(name = "idx_vacina_fabricante", columnList = "fabricante_id"),
           @Index(name = "idx_vacina_codigo_pni", columnList = "codigo_pni"),
           @Index(name = "idx_vacina_codigo_sus", columnList = "codigo_sus"),
           @Index(name = "idx_vacina_status", columnList = "status")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Vacinas extends BaseEntity {

    // ========== IDENTIFICAÇÃO BÁSICA ==========

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(max = 255, message = "Nome da vacina deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    @Column(name = "nome_comercial", length = 255)
    private String nomeComercial;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Size(max = 20, message = "Código PNI deve ter no máximo 20 caracteres")
    @Column(name = "codigo_pni", length = 20, unique = true)
    private String codigoPni; // Código do Programa Nacional de Imunizações

    @Size(max = 20, message = "Código SUS deve ter no máximo 20 caracteres")
    @Column(name = "codigo_sus", length = 20, unique = true)
    private String codigoSus; // Código do Sistema Único de Saúde

    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    @Column(name = "registro_anvisa", length = 50)
    private String registroAnvisa;

    // ========== CLASSIFICAÇÃO ==========

    @Convert(converter = TipoVacinaEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo de vacina é obrigatório")
    private TipoVacinaEnum tipo;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria; // Ex: Rotina, Campanha, Especial

    @Size(max = 100, message = "Grupo alvo deve ter no máximo 100 caracteres")
    @Column(name = "grupo_alvo", length = 100)
    private String grupoAlvo; // Ex: Crianças, Adultos, Idosos, Gestantes

    // ========== FABRICANTE ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesVacina fabricante;

    @Size(max = 50, message = "Lote padrão deve ter no máximo 50 caracteres")
    @Column(name = "lote_padrao", length = 50)
    private String lotePadrao; // Lote de referência (não confundir com lote específico do estoque)

    // ========== ADMINISTRAÇÃO ==========

    @Convert(converter = ViaAdministracaoEnumConverter.class)
    @Column(name = "via_administracao", nullable = false)
    @NotNull(message = "Via de administração é obrigatória")
    private ViaAdministracaoEnum viaAdministracao;

    @Convert(converter = UnidadeMedidaEnumConverter.class)
    @Column(name = "unidade_medida")
    private UnidadeMedidaEnum unidadeMedida; // Unidade de medida da dose

    @Embedded
    private EsquemaVacinal esquemaVacinal;

    // ========== IDADE E CONTRAINDICAÇÕES ==========

    @Embedded
    private IdadeAplicacaoVacina idadeAplicacao;

    @Embedded
    private ContraindicacoesVacina contraindicacoes;

    // ========== CONSERVAÇÃO E ARMAZENAMENTO ==========

    @Embedded
    private ConservacaoVacina conservacao;

    // ========== COMPOSIÇÃO E TECNOLOGIA ==========

    @Embedded
    private ComposicaoVacina composicao;

    // ========== EFICÁCIA E PROTEÇÃO ==========

    @Embedded
    private EficaciaVacina eficacia;

    // ========== REAÇÕES ADVERSAS ==========

    @Embedded
    private ReacoesAdversasVacina reacoesAdversas;

    // ========== CALENDÁRIO VACINAL ==========

    @Embedded
    private CalendarioVacinal calendario;

    // ========== STATUS E DISPONIBILIDADE ==========

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    @Column(name = "disponivel_uso", nullable = false)
    private Boolean disponivelUso = true;

    // ========== DOCUMENTAÇÃO ==========

    @Size(max = 255, message = "Bula deve ter no máximo 255 caracteres")
    @Column(name = "bula", length = 255)
    private String bula; // URL ou caminho da bula

    @Size(max = 255, message = "Ficha técnica deve ter no máximo 255 caracteres")
    @Column(name = "ficha_tecnica", length = 255)
    private String fichaTecnica; // URL ou caminho da ficha técnica

    @Size(max = 255, message = "Manual de uso deve ter no máximo 255 caracteres")
    @Column(name = "manual_uso", length = 255)
    private String manualUso; // URL ou caminho do manual de uso

    // ========== DESCRIÇÃO E OBSERVAÇÕES ==========

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "indicacoes", columnDefinition = "TEXT")
    private String indicacoes; // Indicações de uso

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // ========== INTEGRAÇÃO COM SISTEMAS GOVERNAMENTAIS ==========

    @Embedded
    private IntegracaoGovernamentalVacina integracaoGovernamental;
}
