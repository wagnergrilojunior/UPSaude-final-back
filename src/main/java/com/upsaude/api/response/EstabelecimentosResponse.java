package com.upsaude.api.response;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
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
public class EstabelecimentosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeFantasia;
    private TipoEstabelecimentoEnum tipo;
    private String codigoCnes;
    private String cnpj;
    private NaturezaJuridicaEnum naturezaJuridica;
    private String registroOficial;
    private UUID enderecoPrincipalId;
    private String enderecoPrincipalLogradouro;
    private String enderecoPrincipalNumero;
    private String enderecoPrincipalBairro;
    private String enderecoPrincipalCep;
    private String enderecoPrincipalCidade;
    private String enderecoPrincipalEstado;
    private List<UUID> enderecosSecundariosIds;
    private String telefone;
    private String telefoneSecundario;
    private String fax;
    private String email;
    private String site;
    private UUID responsavelTecnicoId;
    private String responsavelTecnicoNome;
    private UUID responsavelAdministrativoId;
    private String responsavelAdministrativoNome;
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

