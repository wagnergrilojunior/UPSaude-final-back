package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.entity.Medicacao;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacaoMapper;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.service.MedicacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medicações.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoServiceImpl implements MedicacaoService {

    private final MedicacaoRepository medicacaoRepository;
    private final MedicacaoMapper medicacaoMapper;

    @Override
    @Transactional
    public MedicacaoResponse criar(MedicacaoRequest request) {
        log.debug("Criando nova medicação");

        validarDadosBasicos(request);

        Medicacao medicacao = medicacaoMapper.fromRequest(request);
        medicacao.setActive(true);

        Medicacao medicacaoSalvo = medicacaoRepository.save(medicacao);
        log.info("Medicação criada com sucesso. ID: {}", medicacaoSalvo.getId());

        return medicacaoMapper.toResponse(medicacaoSalvo);
    }

    @Override
    @Transactional
    public MedicacaoResponse obterPorId(UUID id) {
        log.debug("Buscando medicação por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        Medicacao medicacao = medicacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

        return medicacaoMapper.toResponse(medicacao);
    }

    @Override
    public Page<MedicacaoResponse> listar(Pageable pageable) {
        log.debug("Listando medicações paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Medicacao> medicacoes = medicacaoRepository.findAll(pageable);
        return medicacoes.map(medicacaoMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicacaoResponse atualizar(UUID id, MedicacaoRequest request) {
        log.debug("Atualizando medicação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        validarDadosBasicos(request);

        Medicacao medicacaoExistente = medicacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

        atualizarDadosMedicacao(medicacaoExistente, request);

        Medicacao medicacaoAtualizado = medicacaoRepository.save(medicacaoExistente);
        log.info("Medicação atualizada com sucesso. ID: {}", medicacaoAtualizado.getId());

        return medicacaoMapper.toResponse(medicacaoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo medicação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        Medicacao medicacao = medicacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(medicacao.getActive())) {
            throw new BadRequestException("Medicação já está inativa");
        }

        medicacao.setActive(false);
        medicacaoRepository.save(medicacao);
        log.info("Medicação excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicacaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da medicação são obrigatórios");
        }
        if (request.getIdentificacao() == null) {
            throw new BadRequestException("Identificação da medicação é obrigatória");
        }
        if (request.getIdentificacao().getPrincipioAtivo() == null || request.getIdentificacao().getPrincipioAtivo().trim().isEmpty()) {
            throw new BadRequestException("Princípio ativo é obrigatório");
        }
        if (request.getIdentificacao().getNomeComercial() == null || request.getIdentificacao().getNomeComercial().trim().isEmpty()) {
            throw new BadRequestException("Nome comercial é obrigatório");
        }
        if (request.getDosagemAdministracao() == null) {
            throw new BadRequestException("Dosagem e administração são obrigatórias");
        }
        if (request.getDosagemAdministracao().getDosagem() == null || request.getDosagemAdministracao().getDosagem().trim().isEmpty()) {
            throw new BadRequestException("Dosagem é obrigatória");
        }
    }

    private void atualizarDadosMedicacao(Medicacao medicacao, MedicacaoRequest request) {
        Medicacao medicacaoAtualizado = medicacaoMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = medicacao.getId();
        com.upsaude.entity.Tenant tenantOriginal = medicacao.getTenant();
        Boolean activeOriginal = medicacao.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicacao.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(medicacaoAtualizado, medicacao);
        
        // Restaura campos de controle
        medicacao.setId(idOriginal);
        medicacao.setTenant(tenantOriginal);
        medicacao.setActive(activeOriginal);
        medicacao.setCreatedAt(createdAtOriginal);
    }
}

