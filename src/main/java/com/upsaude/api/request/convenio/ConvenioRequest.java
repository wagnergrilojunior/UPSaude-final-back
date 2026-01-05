package com.upsaude.api.request.convenio;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
import com.upsaude.util.converter.ModalidadeConvenioEnumDeserializer;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoConvenioEnumDeserializer;
import com.upsaude.validation.annotation.CNPJValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de convênio")
public class ConvenioRequest {
    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @CNPJValido
    private String cnpj;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    private String inscricaoMunicipal;

    @NotNull(message = "Tipo de convênio é obrigatório")
    @JsonDeserialize(using = TipoConvenioEnumDeserializer.class)
    private TipoConvenioEnum tipo;

    // Modalidade é opcional (nullable na Entity) - removido @NotNull para manter consistência
    @JsonDeserialize(using = ModalidadeConvenioEnumDeserializer.class)
    private ModalidadeConvenioEnum modalidade;

    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum status;
    private LocalDate dataCadastro;
    private Boolean redeCredenciadaNacional;
    private Boolean redeCredenciadaRegional;
    private Boolean coberturaObstetricia;
    private Boolean habilitadoTiss;
    private Boolean sincronizarAns;
    private Boolean sincronizarSus;
    private Boolean sincronizarTiss;
}
