package com.upsaude.entity;

import java.util.UUID;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Index;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "usuarios_sistema", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuarios_sistema_user_tenant", columnNames = {"user_id", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_usuarios_sistema_user", columnList = "user_id")
       })
@Data
public class UsuariosSistema extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    @NotNull(message = "Usuário (auth) é obrigatório")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_saude_id")
    private ProfissionaisSaude profissionalSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 50)
    @NotNull(message = "Tipo de usuário é obrigatório")
    private TipoUsuarioSistemaEnum tipoUsuario;

    @Column(name = "nome_exibicao", length = 255)
    private String nomeExibicao;

    
}
