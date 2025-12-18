package com.upsaude.service.support.fabricantesvacina;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesVacinaEnderecoHandler {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    @Transactional
    public Endereco processarEndereco(EnderecoRequest enderecoRequest, UUID tenantId, Tenant tenant) {
        if (enderecoRequest == null) {
            return null;
        }
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        try {
            Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
            endereco.setActive(true);
            endereco.setTenant(tenant);
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }

            if (enderecoRequest.getEstado() != null) {
                Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                    .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                endereco.setEstado(estado);
            }

            if (enderecoRequest.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                    .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                endereco.setCidade(cidade);
            }

            String logradouro = normalizarString(endereco.getLogradouro());
            String numero = normalizarNumero(endereco.getNumero());
            String bairro = normalizarString(endereco.getBairro());
            String cep = normalizarCep(endereco.getCep());

            UUID cidadeId = endereco.getCidade() != null ? endereco.getCidade().getId() : null;
            UUID estadoId = endereco.getEstado() != null ? endereco.getEstado().getId() : null;

            // Se não há dados mínimos para deduplicação, cria novo no tenant
            if ((logradouro == null || logradouro.isBlank()) && (cep == null || cep.isBlank())) {
                endereco.setLogradouro(logradouro);
                endereco.setNumero(numero);
                endereco.setBairro(bairro);
                endereco.setCep(cep);
                Endereco salvo = enderecoRepository.save(endereco);
                log.info("Novo endereço criado (sem logradouro/CEP). ID: {}, tenant: {}", salvo.getId(), tenantId);
                return salvo;
            }

            Optional<Endereco> existenteOpt = enderecoRepository.findByFields(
                endereco.getTipoLogradouro(),
                logradouro,
                numero,
                bairro,
                cep,
                cidadeId,
                estadoId
            );

            if (existenteOpt.isPresent()) {
                Endereco existente = existenteOpt.get();

                // evita reutilização cross-tenant
                if (existente.getTenant() != null && existente.getTenant().getId() != null
                    && existente.getTenant().getId().equals(tenantId)) {
                    log.info("Endereço existente reutilizado. ID: {}, tenant: {}", existente.getId(), tenantId);
                    return existente;
                }

                log.warn("Endereço encontrado pertence a outro tenant. EnderecoId: {}, tenantEndereco: {}, tenantAtual: {}. Criando novo.",
                    existente.getId(),
                    existente.getTenant() != null ? existente.getTenant().getId() : null,
                    tenantId);
            }

            endereco.setLogradouro(logradouro);
            endereco.setNumero(numero);
            endereco.setBairro(bairro);
            endereco.setCep(cep);

            Endereco salvo = enderecoRepository.save(endereco);
            log.info("Novo endereço criado. ID: {}, tenant: {}", salvo.getId(), tenantId);
            return salvo;
        } catch (NotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao processar endereço de fabricante de vacina", e);
            throw new InternalServerErrorException("Erro ao processar endereço", e);
        }
    }

    private String normalizarString(String str) {
        if (str == null) return null;
        String t = str.trim();
        return t.isEmpty() ? null : t;
    }

    private String normalizarNumero(String numero) {
        if (numero == null) return null;
        String t = numero.trim().replaceAll("[^\\d]", "");
        return t.isEmpty() ? null : t;
    }

    private String normalizarCep(String cep) {
        if (cep == null) return null;
        String t = cep.trim().replaceAll("[^\\d]", "");
        return t.isEmpty() ? null : t;
    }
}

