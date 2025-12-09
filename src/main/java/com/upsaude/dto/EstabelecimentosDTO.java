package com.upsaude.dto;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentosDTO {
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
    private EnderecoDTO enderecoPrincipal;
    private String telefone;
    private String telefoneSecundario;
    private String fax;
    private String email;
    private String site;
    private ProfissionaisSaudeDTO responsavelTecnico;
    private ProfissionaisSaudeDTO responsavelAdministrativo;
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
}
