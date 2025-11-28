package com.upsaude.api.request;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.enums.TipoProfissionalEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionaisSaudeRequest {
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    private String nomeCompleto;

    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    private String cpf;

    private LocalDate dataNascimento;

    private SexoEnum sexo;

    private EstadoCivilEnum estadoCivil;

    private EscolaridadeEnum escolaridade;

    private IdentidadeGeneroEnum identidadeGenero;

    private RacaCorEnum racaCor;

    private Boolean temDeficiencia;

    private TipoDeficienciaEnum tipoDeficiencia;

    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    private String rg;

    @Size(max = 50, message = "Órgão emissor do RG deve ter no máximo 50 caracteres")
    private String orgaoEmissorRg;

    @Pattern(regexp = "^[A-Z]{2}$", message = "UF de emissão do RG deve ter 2 letras maiúsculas")
    private String ufEmissaoRg;

    private LocalDate dataEmissaoRg;

    private NacionalidadeEnum nacionalidade;

    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    private String naturalidade;

    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro deve ter no máximo 20 caracteres")
    private String registroProfissional;

    @NotNull(message = "Conselho profissional é obrigatório")
    private UUID conselhoId;

    @Pattern(regexp = "^[A-Z]{2}$", message = "UF do registro deve ter 2 letras maiúsculas")
    private String ufRegistro;

    private OffsetDateTime dataEmissaoRegistro;

    private OffsetDateTime dataValidadeRegistro;

    private StatusAtivoEnum statusRegistro;

    private TipoProfissionalEnum tipoProfissional;

    private List<UUID> especialidadesIds;

    @Size(max = 15, message = "CNS deve ter no máximo 15 caracteres")
    private String cns;

    @Size(max = 10, message = "Código CBO deve ter no máximo 10 caracteres")
    private String codigoCbo;

    @Size(max = 255, message = "Descrição CBO deve ter no máximo 255 caracteres")
    private String descricaoCbo;

    @Size(max = 50, message = "Código ocupacional deve ter no máximo 50 caracteres")
    private String codigoOcupacional;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone institucional deve ter 10 ou 11 dígitos")
    private String telefoneInstitucional;

    @Email(message = "Email institucional inválido")
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    private String emailInstitucional;

    private UUID enderecoProfissionalId;

    private List<UUID> estabelecimentosIds;

    private String observacoes;
}

