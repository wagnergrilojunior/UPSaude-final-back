package com.upsaude.api.request;

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
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.SexoEnumDeserializer;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoDeficienciaEnumDeserializer;
import com.upsaude.util.converter.TipoProfissionalEnumDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionaisSaudeRequest {
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;
    
    @Pattern(regexp = "^$|^\\d{11}$", message = "CPF deve ter 11 dígitos")
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
    private String rg;
    private String orgaoEmissorRg;
    private String ufEmissaoRg;
    private LocalDate dataEmissaoRg;
    private NacionalidadeEnum nacionalidade;
    private String naturalidade;
    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro deve ter no máximo 20 caracteres")
    private String registroProfissional;
    
    @NotNull(message = "Conselho profissional é obrigatório")
    private UUID conselho;
    private String ufRegistro;
    private OffsetDateTime dataEmissaoRegistro;
    private OffsetDateTime dataValidadeRegistro;
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum statusRegistro;
    @JsonDeserialize(using = TipoProfissionalEnumDeserializer.class)
    private TipoProfissionalEnum tipoProfissional;
    private String cns;
    private String codigoCbo;
    private String descricaoCbo;
    private String codigoOcupacional;
    @Pattern(regexp = "^$|^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
    
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @Pattern(regexp = "^$|^\\d{10,11}$", message = "Telefone institucional deve ter 10 ou 11 dígitos")
    private String telefoneInstitucional;
    
    @Email(message = "Email institucional inválido")
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    private String emailInstitucional;
    
    /**
     * Endereço profissional do profissional de saúde.
     * Pode ser fornecido como UUID (endereço existente) ou como objeto EnderecoRequest completo (novo endereço).
     * Se fornecido como objeto completo, será usado findOrCreate para evitar duplicação.
     */
    private UUID enderecoProfissional;
    
    @Valid
    private EnderecoRequest enderecoProfissionalCompleto;
    
    @Builder.Default
    private List<UUID> especialidades = new ArrayList<>();
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
