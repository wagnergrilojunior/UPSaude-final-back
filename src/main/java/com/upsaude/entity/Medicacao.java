package com.upsaude.entity;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.util.converter.ClasseTerapeuticaEnumConverter;
import com.upsaude.util.converter.FormaFarmaceuticaEnumConverter;
import com.upsaude.util.converter.UnidadeMedidaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidade que representa um catálogo de medicamentos conforme padrão SUS/SIGTAP.
 * Armazena informações completas sobre medicamentos para sistemas de prontuário eletrônico.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "medicacoes", schema = "public")
@Data
public class Medicacao extends BaseEntity {

    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(max = 255, message = "Princípio ativo deve ter no máximo 255 caracteres")
    @Column(name = "principio_ativo", nullable = false, length = 255)
    private String principioAtivo;

    @NotBlank(message = "Nome comercial é obrigatório")
    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    @Column(name = "nome_comercial", nullable = false, length = 255)
    private String nomeComercial;

    @Size(max = 100, message = "Via de administração deve ter no máximo 100 caracteres")
    @Column(name = "via_administracao", length = 100)
    private String viaAdministracao;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Código CATMAT (Catálogo de Materiais) do Ministério da Saúde.
     */
    @NotBlank(message = "Código CATMAT é obrigatório")
    @Size(max = 20, message = "Código CATMAT deve ter no máximo 20 caracteres")
    @Column(name = "catmat_codigo", nullable = false, length = 20)
    private String catmatCodigo;

    /**
     * Classe terapêutica do medicamento conforme classificação SUS/SIGTAP.
     */
    @Convert(converter = ClasseTerapeuticaEnumConverter.class)
    @Column(name = "classe_terapeutica")
    private ClasseTerapeuticaEnum classeTerapeutica;

    /**
     * Forma farmacêutica do medicamento conforme Farmacopeia Brasileira/ANVISA.
     */
    @Convert(converter = FormaFarmaceuticaEnumConverter.class)
    @Column(name = "forma_farmaceutica")
    private FormaFarmaceuticaEnum formaFarmaceutica;

    /**
     * Dosagem do medicamento.
     */
    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 50, message = "Dosagem deve ter no máximo 50 caracteres")
    @Column(name = "dosagem", nullable = false, length = 50)
    private String dosagem;

    /**
     * Unidade de medida do medicamento.
     */
    @Convert(converter = UnidadeMedidaEnumConverter.class)
    @Column(name = "unidade_medida")
    private UnidadeMedidaEnum unidadeMedida;

    /**
     * Fabricante do medicamento.
     */
    @NotBlank(message = "Fabricante é obrigatório")
    @Size(max = 255, message = "Fabricante deve ter no máximo 255 caracteres")
    @Column(name = "fabricante", nullable = false, length = 255)
    private String fabricante;

    /**
     * Indica se o medicamento é de uso contínuo.
     */
    @Column(name = "uso_continuo", nullable = false)
    private Boolean usoContinuo = false;

    /**
     * Indica se o medicamento requer receita obrigatória.
     */
    @Column(name = "receita_obrigatoria", nullable = false)
    private Boolean receitaObrigatoria = false;

    /**
     * Indica se o medicamento é controlado (portaria especial).
     */
    @Column(name = "controlado", nullable = false)
    private Boolean controlado = false;
}

