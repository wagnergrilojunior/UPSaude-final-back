package com.upsaude.api.request;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de requisição para criação e atualização de Medicações.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoRequest {

    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(max = 255, message = "Princípio ativo deve ter no máximo 255 caracteres")
    private String principioAtivo;

    @NotBlank(message = "Nome comercial é obrigatório")
    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    private String nomeComercial;

    @Size(max = 100, message = "Via de administração deve ter no máximo 100 caracteres")
    private String viaAdministracao;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    /**
     * Código CATMAT (Catálogo de Materiais) do Ministério da Saúde.
     */
    @NotBlank(message = "Código CATMAT é obrigatório")
    @Size(max = 20, message = "Código CATMAT deve ter no máximo 20 caracteres")
    private String catmatCodigo;

    /**
     * Classe terapêutica do medicamento conforme classificação SUS/SIGTAP.
     */
    private ClasseTerapeuticaEnum classeTerapeutica;

    /**
     * Forma farmacêutica do medicamento conforme Farmacopeia Brasileira/ANVISA.
     */
    private FormaFarmaceuticaEnum formaFarmaceutica;

    /**
     * Dosagem do medicamento.
     */
    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 50, message = "Dosagem deve ter no máximo 50 caracteres")
    private String dosagem;

    /**
     * Unidade de medida do medicamento.
     */
    private UnidadeMedidaEnum unidadeMedida;

    /**
     * Fabricante do medicamento.
     */
    @NotBlank(message = "Fabricante é obrigatório")
    @Size(max = 255, message = "Fabricante deve ter no máximo 255 caracteres")
    private String fabricante;

    /**
     * Indica se o medicamento é de uso contínuo.
     */
    private Boolean usoContinuo;

    /**
     * Indica se o medicamento requer receita obrigatória.
     */
    private Boolean receitaObrigatoria;

    /**
     * Indica se o medicamento é controlado (portaria especial).
     */
    private Boolean controlado;
}

