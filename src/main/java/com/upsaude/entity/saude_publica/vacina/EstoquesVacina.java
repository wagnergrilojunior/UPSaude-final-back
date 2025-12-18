package com.upsaude.entity.saude_publica.vacina;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estoques_vacina", schema = "public")
@Data
public class EstoquesVacina extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacina_id", nullable = false)
    private Vacinas vacina;

    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel;

    @Column(name = "quantidade_minima")
    private Integer quantidadeMinima;

    @Column(name = "quantidade_maxima")
    private Integer quantidadeMaxima;

    @Column(name = "local_armazenamento", length = 255)
    private String localArmazenamento;

    @Column(name = "temperatura_armazenamento", length = 50)
    private String temperaturaArmazenamento;

    @Column(name = "data_validade")
    private java.time.LocalDate dataValidade;
}
