package com.upsaude.entity.sistema.integracao;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.paciente.Paciente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "integracao_gov", schema = "public",
       indexes = {
           @Index(name = "idx_integracao_gov_paciente", columnList = "paciente_id"),
           @Index(name = "idx_integracao_gov_uuid_rnds", columnList = "uuid_rnds"),
           @Index(name = "idx_integracao_gov_id_integracao", columnList = "id_integracao_gov")
       })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class IntegracaoGov extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "uuid_rnds")
    private UUID uuidRnds;

    @Size(max = 100, message = "ID de integração deve ter no máximo 100 caracteres")
    @Column(name = "id_integracao_gov", length = 100)
    private String idIntegracaoGov;

    @Column(name = "data_sincronizacao_gov")
    private LocalDateTime dataSincronizacaoGov;

    @Size(max = 10, message = "INE da equipe deve ter no máximo 10 caracteres")
    @Column(name = "ine_equipe", length = 10)
    private String ineEquipe;

    @Size(max = 10, message = "Microárea deve ter no máximo 10 caracteres")
    @Column(name = "microarea", length = 10)
    private String microarea;

    @Size(max = 7, message = "CNES do estabelecimento deve ter no máximo 7 caracteres")
    @Column(name = "cnes_estabelecimento_origem", length = 7)
    private String cnesEstabelecimentoOrigem;

    @Size(max = 30, message = "Origem do cadastro deve ter no máximo 30 caracteres")
    @Column(name = "origem_cadastro", length = 30)
    private String origemCadastro;
}
