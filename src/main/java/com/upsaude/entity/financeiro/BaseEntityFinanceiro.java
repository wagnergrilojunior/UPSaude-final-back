package com.upsaude.entity.financeiro;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntityFinanceiro extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por_id")
    @JsonIgnore
    @CreatedBy
    private UsuariosSistema criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atualizado_por_id")
    @JsonIgnore
    @LastModifiedBy
    private UsuariosSistema atualizadoPor;
}

