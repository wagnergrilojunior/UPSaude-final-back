package com.upsaude.service.impl;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstabelecimentosMapper;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.EstabelecimentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Estabelecimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosServiceImpl implements EstabelecimentosService {

    private final EstabelecimentosRepository estabelecimentosRepository;
    private final EstabelecimentosMapper estabelecimentosMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;

    @Override
    @Transactional
    @CacheEvict(value = "estabelecimentos", allEntries = true)
    public EstabelecimentosResponse criar(EstabelecimentosRequest request) {
        log.debug("Criando novo estabelecimento");

        validarDadosBasicos(request);

        Estabelecimentos estabelecimento = estabelecimentosMapper.fromRequest(request);
        
        // Carrega e valida endereço principal
        if (request.getEnderecoPrincipal() != null) {
            Endereco enderecoPrincipal = enderecoRepository.findById(request.getEnderecoPrincipal())
                    .orElseThrow(() -> new NotFoundException("Endereço principal não encontrado com ID: " + request.getEnderecoPrincipal()));
            estabelecimento.setEnderecoPrincipal(enderecoPrincipal);
        }

        // Carrega e valida responsável técnico
        if (request.getResponsavelTecnico() != null) {
            ProfissionaisSaude responsavelTecnico = profissionaisSaudeRepository.findById(request.getResponsavelTecnico())
                    .orElseThrow(() -> new NotFoundException("Responsável técnico não encontrado com ID: " + request.getResponsavelTecnico()));
            estabelecimento.setResponsavelTecnico(responsavelTecnico);
        }

        // Carrega e valida responsável administrativo
        if (request.getResponsavelAdministrativo() != null) {
            ProfissionaisSaude responsavelAdmin = profissionaisSaudeRepository.findById(request.getResponsavelAdministrativo())
                    .orElseThrow(() -> new NotFoundException("Responsável administrativo não encontrado com ID: " + request.getResponsavelAdministrativo()));
            estabelecimento.setResponsavelAdministrativo(responsavelAdmin);
        }

        estabelecimento.setActive(true);

        Estabelecimentos estabelecimentoSalvo = estabelecimentosRepository.save(estabelecimento);
        log.info("Estabelecimento criado com sucesso. ID: {}", estabelecimentoSalvo.getId());

        return estabelecimentosMapper.toResponse(estabelecimentoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "estabelecimentos", key = "#id")
    public EstabelecimentosResponse obterPorId(UUID id) {
        log.debug("Buscando estabelecimento por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

        return estabelecimentosMapper.toResponse(estabelecimento);
    }

    @Override
    public Page<EstabelecimentosResponse> listar(Pageable pageable) {
        log.debug("Listando estabelecimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Estabelecimentos> estabelecimentos = estabelecimentosRepository.findAll(pageable);
        return estabelecimentos.map(estabelecimentosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "estabelecimentos", key = "#id")
    public EstabelecimentosResponse atualizar(UUID id, EstabelecimentosRequest request) {
        log.debug("Atualizando estabelecimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        validarDadosBasicos(request);

        Estabelecimentos estabelecimentoExistente = estabelecimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

        atualizarDadosEstabelecimento(estabelecimentoExistente, request);

        Estabelecimentos estabelecimentoAtualizado = estabelecimentosRepository.save(estabelecimentoExistente);
        log.info("Estabelecimento atualizado com sucesso. ID: {}", estabelecimentoAtualizado.getId());

        return estabelecimentosMapper.toResponse(estabelecimentoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "estabelecimentos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo estabelecimento. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(estabelecimento.getActive())) {
            throw new BadRequestException("Estabelecimento já está inativo");
        }

        estabelecimento.setActive(false);
        estabelecimentosRepository.save(estabelecimento);
        log.info("Estabelecimento excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(EstabelecimentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new BadRequestException("Nome do estabelecimento é obrigatório");
        }
        if (request.getTipo() == null) {
            throw new BadRequestException("Tipo do estabelecimento é obrigatório");
        }
    }

    private void atualizarDadosEstabelecimento(Estabelecimentos estabelecimento, EstabelecimentosRequest request) {
        // Identificação básica
        estabelecimento.setNome(request.getNome());
        estabelecimento.setNomeFantasia(request.getNomeFantasia());
        estabelecimento.setTipo(request.getTipo());

        // Identificação oficial
        estabelecimento.setCodigoCnes(request.getCodigoCnes());
        estabelecimento.setCnpj(request.getCnpj());
        estabelecimento.setNaturezaJuridica(request.getNaturezaJuridica());
        estabelecimento.setRegistroOficial(request.getRegistroOficial());

        // Endereço principal
        if (request.getEnderecoPrincipal() != null) {
            Endereco enderecoPrincipal = enderecoRepository.findById(request.getEnderecoPrincipal())
                    .orElseThrow(() -> new NotFoundException("Endereço principal não encontrado com ID: " + request.getEnderecoPrincipal()));
            estabelecimento.setEnderecoPrincipal(enderecoPrincipal);
        } else {
            estabelecimento.setEnderecoPrincipal(null);
        }

        // Contato
        estabelecimento.setTelefone(request.getTelefone());
        estabelecimento.setTelefoneSecundario(request.getTelefoneSecundario());
        estabelecimento.setFax(request.getFax());
        estabelecimento.setEmail(request.getEmail());
        estabelecimento.setSite(request.getSite());

        // Responsáveis
        if (request.getResponsavelTecnico() != null) {
            ProfissionaisSaude responsavelTecnico = profissionaisSaudeRepository.findById(request.getResponsavelTecnico())
                    .orElseThrow(() -> new NotFoundException("Responsável técnico não encontrado com ID: " + request.getResponsavelTecnico()));
            estabelecimento.setResponsavelTecnico(responsavelTecnico);
        } else {
            estabelecimento.setResponsavelTecnico(null);
        }

        if (request.getResponsavelAdministrativo() != null) {
            ProfissionaisSaude responsavelAdmin = profissionaisSaudeRepository.findById(request.getResponsavelAdministrativo())
                    .orElseThrow(() -> new NotFoundException("Responsável administrativo não encontrado com ID: " + request.getResponsavelAdministrativo()));
            estabelecimento.setResponsavelAdministrativo(responsavelAdmin);
        } else {
            estabelecimento.setResponsavelAdministrativo(null);
        }

        estabelecimento.setResponsavelLegalNome(request.getResponsavelLegalNome());
        estabelecimento.setResponsavelLegalCpf(request.getResponsavelLegalCpf());

        // Status e licenciamento
        estabelecimento.setStatusFuncionamento(request.getStatusFuncionamento());
        estabelecimento.setDataAbertura(request.getDataAbertura());
        estabelecimento.setDataLicenciamento(request.getDataLicenciamento());
        estabelecimento.setDataValidadeLicenca(request.getDataValidadeLicenca());
        estabelecimento.setNumeroAlvara(request.getNumeroAlvara());
        estabelecimento.setNumeroLicencaSanitaria(request.getNumeroLicencaSanitaria());
        estabelecimento.setDataValidadeLicencaSanitaria(request.getDataValidadeLicencaSanitaria());

        // Capacidade e infraestrutura
        estabelecimento.setQuantidadeLeitos(request.getQuantidadeLeitos());
        estabelecimento.setQuantidadeConsultorios(request.getQuantidadeConsultorios());
        estabelecimento.setQuantidadeSalasCirurgia(request.getQuantidadeSalasCirurgia());
        estabelecimento.setQuantidadeAmbulatorios(request.getQuantidadeAmbulatorios());
        estabelecimento.setAreaConstruidaMetrosQuadrados(request.getAreaConstruidaMetrosQuadrados());
        estabelecimento.setAreaTotalMetrosQuadrados(request.getAreaTotalMetrosQuadrados());

        // Geolocalização
        estabelecimento.setLatitude(request.getLatitude());
        estabelecimento.setLongitude(request.getLongitude());

        // Observações
        estabelecimento.setObservacoes(request.getObservacoes());
        estabelecimento.setDadosComplementares(request.getDadosComplementares());
    }
}

