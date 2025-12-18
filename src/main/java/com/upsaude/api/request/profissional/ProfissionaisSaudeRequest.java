package com.upsaude.api.request.profissional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.enums.TipoProfissionalEnum;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.EstadoCivilEnumDeserializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.SexoEnumDeserializer;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoDeficienciaEnumDeserializer;
import com.upsaude.util.converter.TipoProfissionalEnumDeserializer;
import com.upsaude.validation.annotation.CNSValido;
import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.TelefoneValido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.upsaude.api.request.paciente.EnderecoRequest;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de profissionais de saúde")
public class ProfissionaisSaudeRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no nome completo")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro deve ter no máximo 20 caracteres")
    private String registroProfissional;

    @NotNull(message = "Conselho profissional é obrigatório")
    private UUID conselho;

    @CPFValido
    private String cpf;
    private LocalDate dataNascimento;
    @JsonDeserialize(using = SexoEnumDeserializer.class)
    private SexoEnum sexo;
    @JsonDeserialize(using = EstadoCivilEnumDeserializer.class)
    private EstadoCivilEnum estadoCivil;
    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;
    @JsonDeserialize(using = IdentidadeGeneroEnumDeserializer.class)
    private IdentidadeGeneroEnum identidadeGenero;
    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;
    @JsonDeserialize(using = TipoDeficienciaEnumDeserializer.class)
    private TipoDeficienciaEnum tipoDeficiencia;
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @Size(max = 10, message = "Órgão emissor RG deve ter no máximo 10 caracteres")
    private String orgaoEmissorRg;

    @Size(max = 2, message = "UF emissão RG deve ter no máximo 2 caracteres")
    private String ufEmissaoRg;

    private LocalDate dataEmissaoRg;

    private NacionalidadeEnum nacionalidade;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos na naturalidade")
    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    private String naturalidade;
    private String ufRegistro;
    private OffsetDateTime dataEmissaoRegistro;
    private OffsetDateTime dataValidadeRegistro;
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum statusRegistro;
    @JsonDeserialize(using = TipoProfissionalEnumDeserializer.class)
    private TipoProfissionalEnum tipoProfissional;
    @CNSValido
    private String cns;

    @Size(max = 10, message = "Código CBO deve ter no máximo 10 caracteres")
    private String codigoCbo;

    @Size(max = 255, message = "Descrição CBO deve ter no máximo 255 caracteres")
    private String descricaoCbo;

    @Size(max = 10, message = "Código ocupacional deve ter no máximo 10 caracteres")
    private String codigoOcupacional;

    @TelefoneValido
    private String telefone;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @TelefoneValido
    private String telefoneInstitucional;

    @EmailValido
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    private String emailInstitucional;

    private UUID enderecoProfissional;

    @Deprecated
    @Valid
    private EnderecoRequest enderecoProfissionalCompleto;

    @Builder.Default
    private Set<UUID> especialidades = new HashSet<>();

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
