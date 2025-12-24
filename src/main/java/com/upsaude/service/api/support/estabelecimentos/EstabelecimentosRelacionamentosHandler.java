package com.upsaude.service.api.support.estabelecimentos;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.geral.EnderecoMapper;
import com.upsaude.repository.paciente.EnderecoRepository;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
import com.upsaude.service.api.geral.EnderecoService;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosRelacionamentosHandler {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final CidadesRepository cidadesRepository;
    private final EstadosRepository estadosRepository;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;

    public Estabelecimentos processarEnderecoPrincipal(EstabelecimentosRequest request, Estabelecimentos estabelecimento, UUID tenantId, Tenant tenant) {
        if (request.getEnderecoPrincipalCompleto() != null) {
            log.debug("Processando endereço principal como objeto completo para estabelecimento");

            EnderecoRequest enderecoRequest = request.getEnderecoPrincipalCompleto();
            boolean temCamposPreenchidos = (enderecoRequest.getLogradouro() != null && !enderecoRequest.getLogradouro().trim().isEmpty()) ||
                (enderecoRequest.getCep() != null && !enderecoRequest.getCep().trim().isEmpty()) ||
                (enderecoRequest.getCidade() != null) ||
                (enderecoRequest.getEstado() != null);

            if (!temCamposPreenchidos) {
                log.warn("Endereço principal completo fornecido mas sem campos preenchidos. Ignorando endereço.");
                estabelecimento.setEnderecoPrincipal(null);
                return estabelecimento;
            }

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

            if ((endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) &&
                (endereco.getCep() == null || endereco.getCep().trim().isEmpty())) {
                log.warn("Endereço sem logradouro nem CEP. Criando novo endereço sem buscar duplicados.");
                Endereco enderecoSalvo = enderecoRepository.save(endereco);
                estabelecimento.setEnderecoPrincipal(enderecoSalvo);
                return estabelecimento;
            }

            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            estabelecimento.setEnderecoPrincipal(enderecoProcessado);
            return estabelecimento;
        }

        if (request.getEnderecoPrincipal() != null) {
            UUID enderecoId = request.getEnderecoPrincipal();
            Endereco enderecoPrincipal = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new NotFoundException("Endereço principal não encontrado com ID: " + enderecoId));

            if (enderecoPrincipal.getTenant() != null && enderecoPrincipal.getTenant().getId() != null
                && !enderecoPrincipal.getTenant().getId().equals(tenantId)) {
                // não vazar info de tenant
                throw new NotFoundException("Endereço principal não encontrado com ID: " + enderecoId);
            }

            estabelecimento.setEnderecoPrincipal(enderecoPrincipal);
            return estabelecimento;
        }

        estabelecimento.setEnderecoPrincipal(null);
        return estabelecimento;
    }

    public Estabelecimentos processarResponsaveis(EstabelecimentosRequest request, Estabelecimentos estabelecimento, UUID tenantId) {
        if (request.getResponsavelTecnico() != null) {
            ProfissionaisSaude rt = profissionaisSaudeTenantEnforcer.validarAcesso(request.getResponsavelTecnico(), tenantId);
            estabelecimento.setResponsavelTecnico(rt);
        } else {
            estabelecimento.setResponsavelTecnico(null);
        }

        if (request.getResponsavelAdministrativo() != null) {
            ProfissionaisSaude ra = profissionaisSaudeTenantEnforcer.validarAcesso(request.getResponsavelAdministrativo(), tenantId);
            estabelecimento.setResponsavelAdministrativo(ra);
        } else {
            estabelecimento.setResponsavelAdministrativo(null);
        }

        return estabelecimento;
    }

    public void validarTenantConsistente(UUID tenantId, Tenant tenant) {
        if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }
    }
}

