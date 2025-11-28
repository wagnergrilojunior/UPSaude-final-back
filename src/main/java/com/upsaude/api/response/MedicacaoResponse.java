package com.upsaude.api.response;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import com.upsaude.enums.UnidadeMedidaEnum;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para Medicações.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoResponse {
    private UUID id;
    private String principioAtivo;
    private String nomeComercial;
    private String viaAdministracao;
    private String descricao;
    private String catmatCodigo;
    private ClasseTerapeuticaEnum classeTerapeutica;
    private FormaFarmaceuticaEnum formaFarmaceutica;
    private String dosagem;
    private UnidadeMedidaEnum unidadeMedida;
    private String fabricante;
    private Boolean usoContinuo;
    private Boolean receitaObrigatoria;
    private Boolean controlado;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

