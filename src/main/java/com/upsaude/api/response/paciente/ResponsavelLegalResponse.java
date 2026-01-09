package com.upsaude.api.response.paciente;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;

import com.upsaude.util.converter.TipoResponsavelEnumDeserializer;
import com.upsaude.util.converter.TipoResponsavelEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.enums.TipoResponsavelEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegalResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private PacienteResponse paciente;
    private String nome;
    private String cpf;
    private String telefone;
    private String rg;
    private String cns;
    @JsonSerialize(using = TipoResponsavelEnumSerializer.class)
    @JsonDeserialize(using = TipoResponsavelEnumDeserializer.class)
    private TipoResponsavelEnum tipoResponsavel;
    private Boolean autorizacaoResponsavel;
}
