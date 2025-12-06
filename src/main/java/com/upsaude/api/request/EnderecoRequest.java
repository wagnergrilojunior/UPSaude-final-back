package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.enums.TipoEnderecoEnum;
import com.upsaude.enums.TipoLogradouroEnum;
import com.upsaude.enums.ZonaDomicilioEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoRequest {
    private TipoLogradouroEnum tipoLogradouro;
    @Size(max = 200, message = "Logradouro deve ter no máximo 200 caracteres")
    private String logradouro;
    
    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    private String numero;
    
    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    private String complemento;
    
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    private String bairro;
    
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve ter 8 dígitos")
    private String cep;
    private String pais;
    private String distrito;
    private String pontoReferencia;
    private Double latitude;
    private Double longitude;
    private TipoEnderecoEnum tipoEndereco;
    private ZonaDomicilioEnum zona;
    @Size(max = 7, message = "Código IBGE município deve ter no máximo 7 caracteres")
    private String codigoIbgeMunicipio;
    
    @Size(max = 10, message = "Microárea deve ter no máximo 10 caracteres")
    private String microarea;
    
    @Size(max = 15, message = "INE da equipe deve ter no máximo 15 caracteres")
    private String ineEquipe;
    
    @Size(max = 20, message = "Quadra deve ter no máximo 20 caracteres")
    private String quadra;
    
    @Size(max = 20, message = "Lote deve ter no máximo 20 caracteres")
    private String lote;
    
    @Size(max = 200, message = "Descrição da zona rural deve ter no máximo 200 caracteres")
    private String zonaRuralDescricao;
    
    @Size(max = 5, message = "Andar deve ter no máximo 5 caracteres")
    private String andar;
    
    @Size(max = 20, message = "Bloco deve ter no máximo 20 caracteres")
    private String bloco;
    
    @NotNull(message = "Sem número é obrigatório")
    @Builder.Default
    private Boolean semNumero = false;
    private UUID estado;
    private UUID cidade;
}
