package com.upsaude.service.api.support.atendimento;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoDomainService {

    private final AtendimentoStatusValidator statusValidator;

    public void aplicarDefaults(Atendimento entity) {
        if (entity.getInformacoes() != null && entity.getInformacoes().getStatusAtendimento() == null) {
            entity.getInformacoes().setStatusAtendimento(StatusAtendimentoEnum.AGENDADO);
        }
        if (entity.getInformacoes() != null && entity.getInformacoes().getDataHora() == null) {
            entity.getInformacoes().setDataHora(java.time.OffsetDateTime.now());
        }
    }

    public void validarTransicaoStatus(Atendimento entity, StatusAtendimentoEnum novoStatus) {
        statusValidator.validarTransicao(entity, novoStatus);
    }

    public void validarPodeInativar(Atendimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar atendimento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Atendimento já está inativo");
        }
    }

    public void validarPodeDeletar(Atendimento entity) {
        log.debug("Validando se atendimento pode ser deletado. ID: {}", entity.getId());

    }
}
