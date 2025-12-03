package com.upsaude.api.request;

import com.upsaude.enums.TipoEnderecoEnum;
import com.upsaude.enums.TipoLogradouroEnum;
import com.upsaude.enums.ZonaDomicilioEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {
    private TipoLogradouroEnum tipoLogradouro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String pais;
    private String distrito;
    private String pontoReferencia;
    private Double latitude;
    private Double longitude;
    private TipoEnderecoEnum tipoEndereco;
    private ZonaDomicilioEnum zona;
    private String codigoIbgeMunicipio;
    private String microarea;
    private String ineEquipe;
    private String quadra;
    private String lote;
    private String zonaRuralDescricao;
    private String andar;
    private String bloco;
    private UUID estado;
    private UUID cidade;
}
