package com.upsaude.service.impl;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ProfissionaisSaudeMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.ProfissionaisSaudeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.util.StringUtils;

/**
 * Implementação do serviço de gerenciamento de Profissionais de Saúde.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeServiceImpl implements ProfissionaisSaudeService {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;

    @Override
    @Transactional
    @CacheEvict(value = "profissionaissaude", allEntries = true)
    public ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request) {
        log.debug("Criando novo profissional de saúde");

        validarDadosBasicos(request);
        validarUnicidade(request);

        ProfissionaisSaude profissional = profissionaisSaudeMapper.fromRequest(request);
        profissional.setActive(true);

        // Carregar especialidades do banco de dados
        if (request.getEspecialidadesIds() != null && !request.getEspecialidadesIds().isEmpty()) {
            List<EspecialidadesMedicas> especialidades = especialidadesMedicasRepository.findAllById(request.getEspecialidadesIds());
            profissional.setEspecialidades(especialidades);
        }

        ProfissionaisSaude profissionalSalvo = profissionaisSaudeRepository.save(profissional);
        log.info("Profissional de saúde criado com sucesso. ID: {}", profissionalSalvo.getId());

        return profissionaisSaudeMapper.toResponse(profissionalSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "profissionaissaude", key = "#id")
    public ProfissionaisSaudeResponse obterPorId(UUID id) {
        log.debug("Buscando profissional de saúde por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

        return profissionaisSaudeMapper.toResponse(profissional);
    }

    @Override
    public Page<ProfissionaisSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando profissionais de saúde paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ProfissionaisSaude> profissionais = profissionaisSaudeRepository.findAll(pageable);
        return profissionais.map(profissionaisSaudeMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionaissaude", key = "#id")
    public ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request) {
        log.debug("Atualizando profissional de saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        validarDadosBasicos(request);

        ProfissionaisSaude profissionalExistente = profissionaisSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

        atualizarDadosProfissional(profissionalExistente, request);

        ProfissionaisSaude profissionalAtualizado = profissionaisSaudeRepository.save(profissionalExistente);
        log.info("Profissional de saúde atualizado com sucesso. ID: {}", profissionalAtualizado.getId());

        return profissionaisSaudeMapper.toResponse(profissionalAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "profissionaissaude", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo profissional de saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do profissional de saúde é obrigatório");
        }

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(profissional.getActive())) {
            throw new BadRequestException("Profissional de saúde já está inativo");
        }

        profissional.setActive(false);
        profissionaisSaudeRepository.save(profissional);
        log.info("Profissional de saúde excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ProfissionaisSaudeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do profissional de saúde são obrigatórios");
        }

        // Validar dados regulatórios
        if (request.getDataValidadeRegistro() != null && request.getDataEmissaoRegistro() != null) {
            if (request.getDataValidadeRegistro().isBefore(request.getDataEmissaoRegistro())) {
                throw new BadRequestException("Data de validade do registro não pode ser anterior à data de emissão");
            }
        }

        // Validar que profissional com registro suspenso ou inativo não pode ser vinculado
        if (request.getStatusRegistro() != null && 
            (request.getStatusRegistro() == com.upsaude.enums.StatusAtivoEnum.SUSPENSO || 
             request.getStatusRegistro() == com.upsaude.enums.StatusAtivoEnum.INATIVO)) {
            log.warn("Profissional sendo cadastrado com status de registro: {}", request.getStatusRegistro());
        }
    }

    private void validarUnicidade(ProfissionaisSaudeRequest request) {
        // Validar unicidade de CPF
        if (request.getCpf() != null && !request.getCpf().trim().isEmpty()) {
            if (profissionaisSaudeRepository.existsByCpf(request.getCpf())) {
                throw new BadRequestException("Já existe um profissional cadastrado com o CPF informado");
            }
        }

        // Validar unicidade de registro profissional (registro + conselho + UF)
        if (request.getRegistroProfissional() != null && request.getConselhoId() != null && request.getUfRegistro() != null) {
            if (profissionaisSaudeRepository.existsByRegistroProfissionalAndConselhoIdAndUfRegistro(
                    request.getRegistroProfissional(), request.getConselhoId(), request.getUfRegistro())) {
                throw new BadRequestException("Já existe um profissional cadastrado com o registro profissional, conselho e UF informados");
            }
        }
    }

    private void atualizarDadosProfissional(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request) {
        ProfissionaisSaude profissionalAtualizado = profissionaisSaudeMapper.fromRequest(request);

        profissional.setNomeCompleto(profissionalAtualizado.getNomeCompleto());
        profissional.setCpf(profissionalAtualizado.getCpf());
        profissional.setDataNascimento(profissionalAtualizado.getDataNascimento());
        profissional.setSexo(profissionalAtualizado.getSexo());
        profissional.setEstadoCivil(profissionalAtualizado.getEstadoCivil());
        profissional.setEscolaridade(profissionalAtualizado.getEscolaridade());
        profissional.setIdentidadeGenero(profissionalAtualizado.getIdentidadeGenero());
        profissional.setRacaCor(profissionalAtualizado.getRacaCor());
        profissional.setTemDeficiencia(profissionalAtualizado.getTemDeficiencia() != null ? profissionalAtualizado.getTemDeficiencia() : false);
        profissional.setTipoDeficiencia(profissionalAtualizado.getTipoDeficiencia());
        profissional.setRg(profissionalAtualizado.getRg());
        profissional.setOrgaoEmissorRg(profissionalAtualizado.getOrgaoEmissorRg());
        profissional.setUfEmissaoRg(profissionalAtualizado.getUfEmissaoRg());
        profissional.setDataEmissaoRg(profissionalAtualizado.getDataEmissaoRg());
        profissional.setNacionalidade(profissionalAtualizado.getNacionalidade());
        profissional.setNaturalidade(profissionalAtualizado.getNaturalidade());
        profissional.setRegistroProfissional(profissionalAtualizado.getRegistroProfissional());
        profissional.setConselho(profissionalAtualizado.getConselho());
        profissional.setUfRegistro(profissionalAtualizado.getUfRegistro());
        profissional.setDataEmissaoRegistro(profissionalAtualizado.getDataEmissaoRegistro());
        profissional.setDataValidadeRegistro(profissionalAtualizado.getDataValidadeRegistro());
        profissional.setStatusRegistro(profissionalAtualizado.getStatusRegistro());
        profissional.setTipoProfissional(profissionalAtualizado.getTipoProfissional());
        profissional.setCns(profissionalAtualizado.getCns());
        profissional.setCodigoCbo(profissionalAtualizado.getCodigoCbo());
        profissional.setDescricaoCbo(profissionalAtualizado.getDescricaoCbo());
        profissional.setCodigoOcupacional(profissionalAtualizado.getCodigoOcupacional());
        profissional.setTelefone(profissionalAtualizado.getTelefone());
        profissional.setEmail(profissionalAtualizado.getEmail());
        profissional.setTelefoneInstitucional(profissionalAtualizado.getTelefoneInstitucional());
        profissional.setEmailInstitucional(profissionalAtualizado.getEmailInstitucional());
        profissional.setEnderecoProfissional(profissionalAtualizado.getEnderecoProfissional());
        profissional.setObservacoes(profissionalAtualizado.getObservacoes());

        // Atualizar especialidades
        if (request.getEspecialidadesIds() != null) {
            if (request.getEspecialidadesIds().isEmpty()) {
                profissional.setEspecialidades(new ArrayList<>());
            } else {
                List<EspecialidadesMedicas> especialidades = especialidadesMedicasRepository.findAllById(request.getEspecialidadesIds());
                profissional.setEspecialidades(especialidades);
            }
        }
    }
}

