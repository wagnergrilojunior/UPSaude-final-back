package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Classe embeddable para informações de integração com sistemas governamentais.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class IntegracaoGovernamentalConvenio {

    public IntegracaoGovernamentalConvenio() {
        this.sincronizarAns = false;
        this.sincronizarTiss = false;
        this.sincronizarSus = false;
        this.codigoSistemaExterno = "";
        this.urlApi = "";
        this.tokenApi = "";
        this.chaveApi = "";
    }

    @Column(name = "sincronizar_ans", nullable = false)
    @Builder.Default
    private Boolean sincronizarAns = false; // Se deve sincronizar com ANS

    @Column(name = "ultima_sincronizacao_ans")
    private OffsetDateTime ultimaSincronizacaoAns; // Data da última sincronização com ANS

    @Column(name = "sincronizar_tiss", nullable = false)
    @Builder.Default
    private Boolean sincronizarTiss = false; // Se deve sincronizar com TISS

    @Column(name = "ultima_sincronizacao_tiss")
    private OffsetDateTime ultimaSincronizacaoTiss; // Data da última sincronização com TISS

    @Column(name = "sincronizar_sus", nullable = false)
    @Builder.Default
    private Boolean sincronizarSus = false; // Se deve sincronizar com SUS

    @Column(name = "ultima_sincronizacao_sus")
    private OffsetDateTime ultimaSincronizacaoSus; // Data da última sincronização com SUS

    @Size(max = 50, message = "Código sistema externo deve ter no máximo 50 caracteres")
    @Column(name = "codigo_sistema_externo", length = 50)
    private String codigoSistemaExterno; // Código em sistema externo de integração

    @Size(max = 255, message = "URL API deve ter no máximo 255 caracteres")
    @Column(name = "url_api", length = 255)
    private String urlApi; // URL da API de integração

    @Size(max = 255, message = "Token API deve ter no máximo 255 caracteres")
    @Column(name = "token_api", length = 255)
    private String tokenApi; // Token para autenticação na API

    @Size(max = 255, message = "Chave API deve ter no máximo 255 caracteres")
    @Column(name = "chave_api", length = 255)
    private String chaveApi; // Chave para autenticação na API
}

