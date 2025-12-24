package com.upsaude.service.api.support.alergias;

import com.upsaude.api.request.alergia.AlergiasRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.paciente.alergia.AlergiasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasValidationService {

    private final AlergiasRepository alergiasRepository;
    private final AlergiasSanitizer sanitizer;

    public void validarObrigatorios(AlergiasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da alergia são obrigatórios");
        }
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new BadRequestException("Nome da alergia é obrigatório e não pode conter apenas caracteres de controle inválidos");
        }

        String nomeSanitizado = sanitizer.sanitizarString(request.getNome().trim());
        if (nomeSanitizado == null || nomeSanitizado.isEmpty()) {
            log.warn("Nome da alergia contém apenas caracteres de controle inválidos");
            throw new BadRequestException("Nome da alergia não pode conter apenas caracteres de controle inválidos");
        }
    }

    public void validarDuplicidade(UUID id, AlergiasRequest request) {
        if (request == null) {
            return;
        }

        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            String nomeSanitizado = sanitizer.sanitizarString(request.getNome().trim());
            if (nomeSanitizado == null || nomeSanitizado.isEmpty()) {
                throw new BadRequestException("Nome da alergia não pode conter apenas caracteres de controle inválidos");
            }

            boolean duplicado = (id == null)
                    ? alergiasRepository.existsByNome(nomeSanitizado)
                    : alergiasRepository.existsByNomeAndIdNot(nomeSanitizado, id);

            if (duplicado) {
                throw new BadRequestException(String.format(
                        "Já existe uma alergia cadastrada com o nome '%s' no banco de dados",
                        request.getNome()));
            }
        }

        if (request.getCodigoInterno() != null && !request.getCodigoInterno().trim().isEmpty()) {
            String codigoSanitizado = sanitizer.sanitizarString(request.getCodigoInterno().trim());
            if (codigoSanitizado == null || codigoSanitizado.isEmpty()) {
                throw new BadRequestException("Código interno não pode conter apenas caracteres de controle inválidos");
            }

            boolean duplicado = (id == null)
                    ? alergiasRepository.existsByCodigoInterno(codigoSanitizado)
                    : alergiasRepository.existsByCodigoInternoAndIdNot(codigoSanitizado, id);

            if (duplicado) {
                throw new BadRequestException(String.format(
                        "Já existe uma alergia cadastrada com o código interno '%s' no banco de dados",
                        request.getCodigoInterno()));
            }
        }
    }
}
