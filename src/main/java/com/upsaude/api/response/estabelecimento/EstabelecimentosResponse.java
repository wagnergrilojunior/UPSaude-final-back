package com.upsaude.api.response.estabelecimento;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.paciente.EnderecoResponse;
import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;


import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
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
    private EnderecoResponse enderecoPrincipal;
    private String telefone;
    private String telefoneSecundario;
    private String fax;
    private String email;
    private String site;
    private ProfissionaisSaudeResponse responsavelTecnico;
    private ProfissionaisSaudeResponse responsavelAdministrativo;
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

    @Builder.Default
    private List<EnderecoResponse> enderecosSecundarios = new ArrayList<>();

    @Builder.Default
    private List<ServicosEstabelecimentoResponse> servicos = new ArrayList<>();

    @Builder.Default
    private List<EquipamentosEstabelecimentoResponse> equipamentos = new ArrayList<>();

    @Builder.Default
    private List<InfraestruturaEstabelecimentoResponse> infraestrutura = new ArrayList<>();

    @Builder.Default
    private List<EquipeSaudeResponse> equipes = new ArrayList<>();
}
