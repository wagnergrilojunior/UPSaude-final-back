package com.upsaude.api.request;

import com.upsaude.enums.TipoDeficienciaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de requisição para criação e atualização de Deficiências.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeficienciasRequest {

    @NotBlank(message = "Nome da deficiência é obrigatório")
    @Size(max = 100, message = "Nome da deficiência deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    /**
     * Tipo de deficiência conforme classificação SUS/e-SUS/APS.
     */
    private TipoDeficienciaEnum tipoDeficiencia;

    /**
     * Código CID-10 relacionado à deficiência.
     */
    @Pattern(regexp = "^[A-Z]\\d{2}(\\.\\d{1,2})?$", message = "Código CID-10 deve seguir o formato A99 ou A99.99")
    @Size(max = 10, message = "Código CID-10 deve ter no máximo 10 caracteres")
    private String cid10Relacionado;

    /**
     * Indica se a deficiência é permanente segundo critérios do SUS.
     */
    @NotNull(message = "Campo permanente é obrigatório")
    private Boolean permanente;

    /**
     * Indica se o paciente exige acompanhamento contínuo pela equipe ESF/APS.
     */
    @NotNull(message = "Campo acompanhamentoContinuo é obrigatório")
    private Boolean acompanhamentoContinuo;
}

