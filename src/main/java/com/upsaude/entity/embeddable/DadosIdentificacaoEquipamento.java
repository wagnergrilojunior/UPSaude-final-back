package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoEquipamentoEnum;
import com.upsaude.util.converter.TipoEquipamentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosIdentificacaoEquipamento {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nome_comercial", length = 100)
    private String nomeComercial;

    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Column(name = "codigo_cnes", length = 20)
    private String codigoCnes;

    @Column(name = "registro_anvisa", length = 50)
    private String registroAnvisa;

    @Convert(converter = TipoEquipamentoEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    private TipoEquipamentoEnum tipo;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "subcategoria", length = 100)
    private String subcategoria;

    @Column(name = "classe_risco", length = 50)
    private String classeRisco;
}
