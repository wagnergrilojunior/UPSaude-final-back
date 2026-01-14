package com.upsaude.entity.estabelecimento;

import com.upsaude.entity.BaseEntityWithoutEstabelecimento;
import com.upsaude.enums.TipoContaBancariaEnum;
import com.upsaude.enums.TipoChavePixEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "conta_bancaria_estabelecimento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uk_conta_bancaria_banco_agencia_conta_estabelecimento", columnNames = {
                "banco_codigo", "agencia", "numero_conta", "estabelecimento_id" })
}, indexes = {
        @Index(name = "idx_conta_bancaria_estabelecimento_id", columnList = "estabelecimento_id"),
        @Index(name = "idx_conta_bancaria_banco_codigo", columnList = "banco_codigo"),
        @Index(name = "idx_conta_bancaria_principal", columnList = "estabelecimento_id, conta_principal")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ContaBancariaEstabelecimento extends BaseEntityWithoutEstabelecimento {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @Column(name = "banco_codigo", nullable = false, length = 10)
    private String bancoCodigo;

    @Column(name = "banco_nome", nullable = false, length = 255)
    private String bancoNome;

    @Column(name = "agencia", nullable = false, length = 20)
    private String agencia;

    @Column(name = "agencia_digito", length = 2)
    private String agenciaDigito;

    @Column(name = "numero_conta", nullable = false, length = 50)
    private String numeroConta;

    @Column(name = "conta_digito", length = 2)
    private String contaDigito;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false, length = 30)
    private TipoContaBancariaEnum tipoConta;

    @Column(name = "conta_principal", nullable = false, columnDefinition = "boolean default false")
    private Boolean contaPrincipal = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_chave_pix", length = 30)
    private TipoChavePixEnum tipoChavePix;

    @Column(name = "chave_pix", length = 255)
    private String chavePix;

    @Column(name = "titular_nome", nullable = false, length = 255)
    private String titularNome;

    @Column(name = "titular_cpf_cnpj", nullable = false, length = 18)
    private String titularCpfCnpj;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
