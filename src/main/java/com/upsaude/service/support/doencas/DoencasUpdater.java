package com.upsaude.service.support.doencas;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.doencas.DoencasRequest;
import com.upsaude.entity.clinica.doencas.Doencas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.embeddable.ClassificacaoDoencaMapper;
import com.upsaude.mapper.embeddable.EpidemiologiaDoencaMapper;
import com.upsaude.mapper.embeddable.SintomasDoencaMapper;
import com.upsaude.mapper.embeddable.TratamentoPadraoDoencaMapper;
import com.upsaude.repository.clinica.doencas.DoencasRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasUpdater {

    private final DoencasRepository repository;
    private final DoencasValidationService validationService;
    private final ClassificacaoDoencaMapper classificacaoDoencaMapper;
    private final SintomasDoencaMapper sintomasDoencaMapper;
    private final TratamentoPadraoDoencaMapper tratamentoPadraoDoencaMapper;
    private final EpidemiologiaDoencaMapper epidemiologiaDoencaMapper;

    public Doencas atualizar(UUID id, DoencasRequest request) {
        Doencas existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Doença não encontrada com ID: " + id));

        validationService.validarObrigatorios(request);
        validationService.validarDuplicidade(id, request);

        atualizarDadosDoenca(existente, request);

        Doencas salvo = repository.save(Objects.requireNonNull(existente));
        log.info("Doença atualizada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }

    private void atualizarDadosDoenca(Doencas doenca, DoencasRequest request) {
        if (request.getNome() != null) {
            doenca.setNome(request.getNome());
        }
        if (request.getNomeCientifico() != null) {
            doenca.setNomeCientifico(request.getNomeCientifico());
        }
        if (request.getCodigoInterno() != null) {
            doenca.setCodigoInterno(request.getCodigoInterno());
        }

        if (request.getClassificacao() != null) {
            if (doenca.getClassificacao() == null) {
                doenca.setClassificacao(classificacaoDoencaMapper.toEntity(request.getClassificacao()));
            } else {
                classificacaoDoencaMapper.updateFromRequest(request.getClassificacao(), doenca.getClassificacao());
            }
        }
        if (request.getSintomas() != null) {
            if (doenca.getSintomas() == null) {
                doenca.setSintomas(sintomasDoencaMapper.toEntity(request.getSintomas()));
            } else {
                sintomasDoencaMapper.updateFromRequest(request.getSintomas(), doenca.getSintomas());
            }
        }
        if (request.getTratamentoPadrao() != null) {
            if (doenca.getTratamentoPadrao() == null) {
                doenca.setTratamentoPadrao(tratamentoPadraoDoencaMapper.toEntity(request.getTratamentoPadrao()));
            } else {
                tratamentoPadraoDoencaMapper.updateFromRequest(request.getTratamentoPadrao(), doenca.getTratamentoPadrao());
            }
        }
        if (request.getEpidemiologia() != null) {
            if (doenca.getEpidemiologia() == null) {
                doenca.setEpidemiologia(epidemiologiaDoencaMapper.toEntity(request.getEpidemiologia()));
            } else {
                epidemiologiaDoencaMapper.updateFromRequest(request.getEpidemiologia(), doenca.getEpidemiologia());
            }
        }
        if (request.getDescricao() != null) {
            doenca.setDescricao(request.getDescricao());
        }
        if (request.getCausas() != null) {
            doenca.setCausas(request.getCausas());
        }
        if (request.getFisiopatologia() != null) {
            doenca.setFisiopatologia(request.getFisiopatologia());
        }
        if (request.getPrognostico() != null) {
            doenca.setPrognostico(request.getPrognostico());
        }
        if (request.getObservacoes() != null) {
            doenca.setObservacoes(request.getObservacoes());
        }

        // CidPrincipal removido - CidDoencas foi deletado
    }
}

