package com.upsaude.api.response.clinica.prontuario;

import com.upsaude.api.response.diagnostico.Ciap2Response;
import com.upsaude.api.response.referencia.cid.Cid10SubcategoriaResponse;
import java.time.LocalDate;
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
public class DoencaPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private Cid10SubcategoriaResponse diagnostico;
    private Ciap2Response ciap2;
    private String tipoCatalogo;
    private String codigo;
    private String descricaoPersonalizada;
    private LocalDate dataDiagnostico;
    private String status;
    private Boolean cronico;
    private Boolean ativa;
    private OffsetDateTime dataSincronizacao;
    private String observacoes;
}
