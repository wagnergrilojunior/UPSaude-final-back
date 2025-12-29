package com.upsaude.api.response.embeddable;

import com.upsaude.enums.TipoDeficienciaEnum;
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
public class DadosDeficienciaProfissionalResponse {
    private Boolean temDeficiencia;
    private TipoDeficienciaEnum tipoDeficiencia;
}

