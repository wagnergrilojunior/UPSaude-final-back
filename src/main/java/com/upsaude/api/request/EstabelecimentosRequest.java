package com.upsaude.api.request;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
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
public class EstabelecimentosRequest {
    @NotBlank(message = "Nome do estabelecimento é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;
    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    private String nomeFantasia;
    
    @NotNull(message = "Tipo de estabelecimento é obrigatório")
    private TipoEstabelecimentoEnum tipo;
    
    @Size(max = 7, message = "Código CNES deve ter no máximo 7 caracteres")
    private String codigoCnes;
    
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    private String cnpj;
    private NaturezaJuridicaEnum naturezaJuridica;
    private String registroOficial;
    private UUID enderecoPrincipal;
    private String telefone;
    private String telefoneSecundario;
    private String fax;
    private String email;
    private String site;
    private UUID responsavelTecnico;
    private UUID responsavelAdministrativo;
    private String responsavelLegalNome;
    private String responsavelLegalCpf;
    private StatusFuncionamentoEnum statusFuncionamento;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataLicenciamento;
    private OffsetDateTime dataValidadeLicenca;
    private String numeroAlvara;
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
