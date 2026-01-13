package com.upsaude.entity.referencia.profissional;

import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conselhos_profissionais", schema = "public", indexes = {
        @Index(name = "idx_conselho_codigo", columnList = "codigo"),
        @Index(name = "idx_conselho_sigla", columnList = "sigla")
})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConselhoProfissional extends BaseEntityWithoutTenant {

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "sigla", nullable = false, length = 20)
    private String sigla;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "tipo", length = 50)
    private String tipo;
}
