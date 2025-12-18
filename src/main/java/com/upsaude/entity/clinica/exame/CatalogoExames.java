package com.upsaude.entity.clinica.exame;
import com.upsaude.entity.BaseEntity;

import com.upsaude.enums.TipoExameEnum;
import com.upsaude.util.converter.TipoExameEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = ""
		+ "", schema = "public",
       indexes = {
           @Index(name = "idx_catalogo_exame_tipo", columnList = "tipo_exame"),
           @Index(name = "idx_catalogo_exame_codigo", columnList = "codigo"),
           @Index(name = "idx_catalogo_exame_estabelecimento", columnList = "estabelecimento_id")
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_catalogo_exame_codigo", columnNames = {"codigo", "tenant_id"})
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class CatalogoExames extends BaseEntity {

    @Convert(converter = TipoExameEnumConverter.class)
    @Column(name = "tipo_exame", nullable = false)
    @NotNull(message = "Tipo de exame é obrigatório")
    private TipoExameEnum tipoExame;

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "requer_preparacao")
    private Boolean requerPreparacao = false;

    @Column(name = "instrucoes_preparacao", columnDefinition = "TEXT")
    private String instrucoesPreparacao;

    @Column(name = "prazo_resultado_dias")
    private Integer prazoResultadoDias;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
