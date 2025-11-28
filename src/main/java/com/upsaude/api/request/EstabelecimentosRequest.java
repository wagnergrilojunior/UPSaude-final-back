package com.upsaude.api.request;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentosRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;

    @NotNull(message = "Tipo é obrigatório")
    private TipoEstabelecimentoEnum tipo;

    @Size(max = 7, message = "Código CNES deve ter no máximo 7 caracteres")
    private String codigoCnes;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;

    private NaturezaJuridicaEnum naturezaJuridica;

    @Size(max = 50, message = "Registro oficial deve ter no máximo 50 caracteres")
    private String registroOficial;

    private UUID enderecoPrincipalId;

    private List<UUID> enderecosSecundariosIds;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone secundário deve ter 10 ou 11 dígitos")
    private String telefoneSecundario;

    @Pattern(regexp = "^\\d{10,11}$", message = "Fax deve ter 10 ou 11 dígitos")
    private String fax;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 500, message = "Site deve ter no máximo 500 caracteres")
    private String site;

    private UUID responsavelTecnicoId;

    private UUID responsavelAdministrativoId;

    @Size(max = 255, message = "Nome do responsável legal deve ter no máximo 255 caracteres")
    private String responsavelLegalNome;

    @Pattern(regexp = "^\\d{11}$", message = "CPF do responsável legal deve ter 11 dígitos")
    private String responsavelLegalCpf;

    private StatusFuncionamentoEnum statusFuncionamento;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataLicenciamento;

    private OffsetDateTime dataValidadeLicenca;

    @Size(max = 100, message = "Número do alvará deve ter no máximo 100 caracteres")
    private String numeroAlvara;

    @Size(max = 100, message = "Número da licença sanitária deve ter no máximo 100 caracteres")
    private String numeroLicencaSanitaria;

    private OffsetDateTime dataValidadeLicencaSanitaria;

    private Integer quantidadeLeitos;

    private Integer quantidadeConsultorios;

    private Integer quantidadeSalasCirurgia;

    private Integer quantidadeAmbulatorios;

    private Double areaConstruidaMetrosQuadrados;

    private Double areaTotalMetrosQuadrados;

    private Double latitude;

    private Double longitude;

    private String observacoes;

    private String dadosComplementares;
}

