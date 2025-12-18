package com.upsaude.service.support.medicacao;

import org.springframework.stereotype.Service;

import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.entity.embeddable.ConservacaoArmazenamentoMedicamento;
import com.upsaude.entity.embeddable.ContraindicacoesPrecaucoesMedicamento;
import com.upsaude.entity.embeddable.DosagemAdministracaoMedicamento;
import com.upsaude.entity.embeddable.IdentificacaoMedicamento;
import com.upsaude.entity.embeddable.RegistroControleMedicamento;

@Service
public class MedicacaoSanitizer {

    public void garantirValoresPadrao(Medicacao medicacao) {
        if (medicacao == null) {
            return;
        }

        if (medicacao.getIdentificacao() == null) {
            medicacao.setIdentificacao(IdentificacaoMedicamento.builder()
                .principioAtivo("Não informado")
                .nomeComercial("Não informado")
                .build());
        } else {
            IdentificacaoMedicamento identificacao = medicacao.getIdentificacao();
            if (identificacao.getPrincipioAtivo() == null || identificacao.getPrincipioAtivo().trim().isEmpty()) {
                identificacao.setPrincipioAtivo("Não informado");
            }
            if (identificacao.getNomeComercial() == null || identificacao.getNomeComercial().trim().isEmpty()) {
                identificacao.setNomeComercial("Não informado");
            }
        }

        if (medicacao.getDosagemAdministracao() == null) {
            medicacao.setDosagemAdministracao(DosagemAdministracaoMedicamento.builder()
                .dosagem("Não informado")
                .build());
        } else {
            DosagemAdministracaoMedicamento dosagem = medicacao.getDosagemAdministracao();
            if (dosagem.getDosagem() == null || dosagem.getDosagem().trim().isEmpty()) {
                dosagem.setDosagem("Não informado");
            }
        }

        if (medicacao.getContraindicacoesPrecaucoes() == null) {
            medicacao.setContraindicacoesPrecaucoes(ContraindicacoesPrecaucoesMedicamento.builder()
                .gestantePode(false)
                .lactantePode(false)
                .criancaPode(true)
                .idosoPode(true)
                .build());
        } else {
            ContraindicacoesPrecaucoesMedicamento contraindicacoes = medicacao.getContraindicacoesPrecaucoes();
            if (contraindicacoes.getGestantePode() == null) {
                contraindicacoes.setGestantePode(false);
            }
            if (contraindicacoes.getLactantePode() == null) {
                contraindicacoes.setLactantePode(false);
            }
            if (contraindicacoes.getCriancaPode() == null) {
                contraindicacoes.setCriancaPode(true);
            }
            if (contraindicacoes.getIdosoPode() == null) {
                contraindicacoes.setIdosoPode(true);
            }
        }

        if (medicacao.getConservacaoArmazenamento() == null) {
            medicacao.setConservacaoArmazenamento(ConservacaoArmazenamentoMedicamento.builder()
                .protegerLuz(false)
                .protegerUmidade(false)
                .build());
        } else {
            ConservacaoArmazenamentoMedicamento conservacao = medicacao.getConservacaoArmazenamento();
            if (conservacao.getProtegerLuz() == null) {
                conservacao.setProtegerLuz(false);
            }
            if (conservacao.getProtegerUmidade() == null) {
                conservacao.setProtegerUmidade(false);
            }
        }

        if (medicacao.getRegistroControle() == null) {
            medicacao.setRegistroControle(RegistroControleMedicamento.builder()
                .controlado(false)
                .receitaObrigatoria(false)
                .usoContinuo(false)
                .medicamentoEspecial(false)
                .medicamentoExcepcional(false)
                .build());
        } else {
            RegistroControleMedicamento registro = medicacao.getRegistroControle();
            if (registro.getControlado() == null) {
                registro.setControlado(false);
            }
            if (registro.getReceitaObrigatoria() == null) {
                registro.setReceitaObrigatoria(false);
            }
            if (registro.getUsoContinuo() == null) {
                registro.setUsoContinuo(false);
            }
            if (registro.getMedicamentoEspecial() == null) {
                registro.setMedicamentoEspecial(false);
            }
            if (registro.getMedicamentoExcepcional() == null) {
                registro.setMedicamentoExcepcional(false);
            }
        }
    }
}

