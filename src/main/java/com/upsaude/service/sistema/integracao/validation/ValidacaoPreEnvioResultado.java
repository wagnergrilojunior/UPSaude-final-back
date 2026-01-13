package com.upsaude.service.sistema.integracao.validation;

import com.upsaude.enums.TipoErroIntegracaoEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ValidacaoPreEnvioResultado {
    private boolean valido;
    private TipoErroIntegracaoEnum tipoErro;
    private String mensagemErro;
    @Builder.Default
    private List<String> detalhesErros = new ArrayList<>();

    public static ValidacaoPreEnvioResultado sucesso() {
        return ValidacaoPreEnvioResultado.builder()
                .valido(true)
                .build();
    }

    public static ValidacaoPreEnvioResultado erro(TipoErroIntegracaoEnum tipoErro, String mensagemErro) {
        return ValidacaoPreEnvioResultado.builder()
                .valido(false)
                .tipoErro(tipoErro)
                .mensagemErro(mensagemErro)
                .build();
    }

    public static ValidacaoPreEnvioResultado erro(TipoErroIntegracaoEnum tipoErro, String mensagemErro, List<String> detalhesErros) {
        return ValidacaoPreEnvioResultado.builder()
                .valido(false)
                .tipoErro(tipoErro)
                .mensagemErro(mensagemErro)
                .detalhesErros(detalhesErros != null ? detalhesErros : new ArrayList<>())
                .build();
    }

    public void adicionarDetalheErro(String detalhe) {
        if (detalhesErros == null) {
            detalhesErros = new ArrayList<>();
        }
        detalhesErros.add(detalhe);
    }
}
