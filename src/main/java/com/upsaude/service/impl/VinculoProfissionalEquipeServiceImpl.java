package com.upsaude.service.impl;

import com.upsaude.api.request.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.VinculoProfissionalEquipeResponse;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VinculoProfissionalEquipeMapper;
import com.upsaude.repository.EquipeSaudeRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.VinculoProfissionalEquipeRepository;
import com.upsaude.service.VinculoProfissionalEquipeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de vínculos entre profissionais e equipes de saúde.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VinculoProfissionalEquipeServiceImpl implements VinculoProfissionalEquipeService {

    private final VinculoProfissionalEquipeRepository vinculoRepository;
    private final VinculoProfissionalEquipeMapper vinculoMapper;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final EquipeSaudeRepository equipeSaudeRepository;

    @Override
    @Transactional
    public VinculoProfissionalEquipeResponse criar(VinculoProfissionalEquipeRequest request) {
        log.debug("Criando novo vínculo de profissional com equipe");

        validarDadosBasicos(request);
        validarIntegridade(request, null);

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissionalId()));

        EquipeSaude equipe = equipeSaudeRepository.findById(request.getEquipeId())
                .orElseThrow(() -> new NotFoundException("Equipe não encontrada com ID: " + request.getEquipeId()));

        // Valida se o profissional está ativo e habilitado
        if (profissional.getStatusRegistro() == null || profissional.getStatusRegistro().isSuspenso() || profissional.getStatusRegistro().isInativo()) {
            throw new BadRequestException("Não é possível vincular um profissional com registro " + profissional.getStatusRegistro().getDescricao());
        }

        // Valida que o profissional e a equipe pertencem ao mesmo tenant
        if (!profissional.getTenant().getId().equals(equipe.getTenant().getId())) {
            throw new BadRequestException("Profissional e equipe devem pertencer ao mesmo tenant.");
        }

        // Valida que a equipe está ativa
        if (equipe.getStatus() == null || equipe.getStatus().isInativo()) {
            throw new BadRequestException("Não é possível vincular profissional a uma equipe inativa.");
        }

        VinculoProfissionalEquipe vinculo = vinculoMapper.fromRequest(request);
        vinculo.setProfissional(profissional);
        vinculo.setEquipe(equipe);
        vinculo.setTenant(profissional.getTenant()); // Garante que o vínculo tenha o tenant do profissional
        vinculo.setActive(true);

        VinculoProfissionalEquipe vinculoSalvo = vinculoRepository.save(vinculo);
        log.info("Vínculo de profissional com equipe criado com sucesso. ID: {}", vinculoSalvo.getId());

        return vinculoMapper.toResponse(vinculoSalvo);
    }

    @Override
    @Transactional
    public VinculoProfissionalEquipeResponse obterPorId(UUID id) {
        log.debug("Buscando vínculo de profissional com equipe por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

        VinculoProfissionalEquipe vinculo = vinculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo não encontrado com ID: " + id));

        return vinculoMapper.toResponse(vinculo);
    }

    @Override
    public Page<VinculoProfissionalEquipeResponse> listar(Pageable pageable) {
        log.debug("Listando vínculos de profissional com equipe paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<VinculoProfissionalEquipe> vinculos = vinculoRepository.findAll(pageable);
        return vinculos.map(vinculoMapper::toResponse);
    }

    @Override
    public Page<VinculoProfissionalEquipeResponse> listarPorProfissional(UUID profissionalId, Pageable pageable) {
        log.debug("Listando vínculos por profissional. ID do Profissional: {}", profissionalId);
        if (profissionalId == null) {
            throw new BadRequestException("ID do profissional é obrigatório");
        }
        Page<VinculoProfissionalEquipe> vinculos = vinculoRepository.findByProfissionalIdOrderByDataInicioDesc(profissionalId, pageable);
        return vinculos.map(vinculoMapper::toResponse);
    }

    @Override
    public Page<VinculoProfissionalEquipeResponse> listarPorEquipe(UUID equipeId, Pageable pageable) {
        log.debug("Listando vínculos por equipe. ID da Equipe: {}", equipeId);
        if (equipeId == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }
        Page<VinculoProfissionalEquipe> vinculos = vinculoRepository.findByEquipeIdOrderByDataInicioDesc(equipeId, pageable);
        return vinculos.map(vinculoMapper::toResponse);
    }

    @Override
    public Page<VinculoProfissionalEquipeResponse> listarPorTipoVinculo(TipoVinculoProfissionalEnum tipoVinculo, UUID equipeId, Pageable pageable) {
        log.debug("Listando vínculos por tipo de vínculo: {} e equipe: {}", tipoVinculo, equipeId);
        if (tipoVinculo == null) {
            throw new BadRequestException("Tipo de vínculo é obrigatório");
        }
        if (equipeId == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }
        Page<VinculoProfissionalEquipe> vinculos = vinculoRepository.findByTipoVinculoAndEquipeId(tipoVinculo, equipeId, pageable);
        return vinculos.map(vinculoMapper::toResponse);
    }

    @Override
    public Page<VinculoProfissionalEquipeResponse> listarPorStatus(StatusAtivoEnum status, UUID equipeId, Pageable pageable) {
        log.debug("Listando vínculos por status: {} e equipe: {}", status, equipeId);
        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }
        if (equipeId == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }
        Page<VinculoProfissionalEquipe> vinculos = vinculoRepository.findByStatusAndEquipeId(status, equipeId, pageable);
        return vinculos.map(vinculoMapper::toResponse);
    }

    @Override
    @Transactional
    public VinculoProfissionalEquipeResponse atualizar(UUID id, VinculoProfissionalEquipeRequest request) {
        log.debug("Atualizando vínculo de profissional com equipe. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

        validarDadosBasicos(request);
        validarIntegridade(request, id);

        VinculoProfissionalEquipe vinculoExistente = vinculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo não encontrado com ID: " + id));

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissionalId()));

        EquipeSaude equipe = equipeSaudeRepository.findById(request.getEquipeId())
                .orElseThrow(() -> new NotFoundException("Equipe não encontrada com ID: " + request.getEquipeId()));

        // Valida se o profissional está ativo e habilitado
        if (profissional.getStatusRegistro() == null || profissional.getStatusRegistro().isSuspenso() || profissional.getStatusRegistro().isInativo()) {
            throw new BadRequestException("Não é possível vincular um profissional com registro " + profissional.getStatusRegistro().getDescricao());
        }

        // Valida que o profissional e a equipe pertencem ao mesmo tenant
        if (!profissional.getTenant().getId().equals(equipe.getTenant().getId())) {
            throw new BadRequestException("Profissional e equipe devem pertencer ao mesmo tenant.");
        }

        atualizarDadosVinculo(vinculoExistente, request, profissional, equipe);

        VinculoProfissionalEquipe vinculoAtualizado = vinculoRepository.save(vinculoExistente);
        log.info("Vínculo de profissional com equipe atualizado com sucesso. ID: {}", vinculoAtualizado.getId());

        return vinculoMapper.toResponse(vinculoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo vínculo de profissional com equipe. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vínculo é obrigatório");
        }

        VinculoProfissionalEquipe vinculo = vinculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vínculo não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(vinculo.getActive())) {
            throw new BadRequestException("Vínculo já está inativo");
        }

        vinculo.setActive(false);
        vinculoRepository.save(vinculo);
        log.info("Vínculo de profissional com equipe excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(VinculoProfissionalEquipeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do vínculo são obrigatórios");
        }
        if (request.getDataFim() != null && request.getDataFim().isBefore(request.getDataInicio())) {
            throw new BadRequestException("A data de fim do vínculo não pode ser anterior à data de início.");
        }
    }

    private void validarIntegridade(VinculoProfissionalEquipeRequest request, UUID currentId) {
        // Valida se já existe vínculo ativo entre o profissional e a equipe
        Optional<VinculoProfissionalEquipe> existingVinculo = vinculoRepository
                .findByProfissionalIdAndEquipeId(request.getProfissionalId(), request.getEquipeId());

        if (existingVinculo.isPresent() && (currentId == null || !existingVinculo.get().getId().equals(currentId))) {
            throw new BadRequestException("Já existe um vínculo ativo entre este profissional e esta equipe.");
        }
    }

    private void atualizarDadosVinculo(VinculoProfissionalEquipe vinculo, VinculoProfissionalEquipeRequest request,
                                       ProfissionaisSaude profissional, EquipeSaude equipe) {
        vinculo.setProfissional(profissional);
        vinculo.setEquipe(equipe);
        vinculo.setDataInicio(request.getDataInicio());
        vinculo.setDataFim(request.getDataFim());
        vinculo.setTipoVinculo(request.getTipoVinculo());
        vinculo.setFuncaoEquipe(request.getFuncaoEquipe());
        vinculo.setCargaHorariaSemanal(request.getCargaHorariaSemanal());
        vinculo.setStatus(request.getStatus());
        vinculo.setObservacoes(request.getObservacoes());
    }
}

