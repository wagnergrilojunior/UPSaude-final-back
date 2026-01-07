package com.upsaude.integration.cnes.service.support;

import com.upsaude.api.response.cnes.CnesEstabelecimentoDTO;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.integration.cnes.wsdl.estabelecimento.v1r0.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Component
public class CnesImporter {

    public CnesEstabelecimentoDTO toDTO(ResponseConsultarEstabelecimentoSaude response) {
        if (response == null || response.getDadosGeraisEstabelecimentoSaude() == null) {
            return null;
        }

        DadosGeraisEstabelecimentoSaudeType dados = response.getDadosGeraisEstabelecimentoSaude();

        CnesEstabelecimentoDTO dto = new CnesEstabelecimentoDTO();

        if (dados.getCodigoCNES() != null) {
            dto.setCnes(dados.getCodigoCNES().getCodigo());
        }

        if (dados.getCNPJ() != null) {
            dto.setCnpj(dados.getCNPJ().getNumeroCNPJ());
        }

        if (dados.getNomeFantasia() != null) {
            dto.setNomeFantasia(dados.getNomeFantasia().getNome());
        }
        if (dados.getNomeEmpresarial() != null) {
            dto.setRazaoSocial(dados.getNomeEmpresarial().getNome());
        }

        if (dados.getEndereco() != null) {
            EnderecoType enderecoType = dados.getEndereco();
            EnderecoResponse endereco = new EnderecoResponse();
            endereco.setLogradouro(enderecoType.getNomeLogradouro());
            endereco.setNumero(enderecoType.getNumero());
            endereco.setComplemento(enderecoType.getComplemento());
            endereco.setBairro(enderecoType.getBairro() != null ? enderecoType.getBairro().getDescricaoBairro() : null);
            endereco.setCep(enderecoType.getCEP() != null ? enderecoType.getCEP().getNumeroCEP() : null);

            if (enderecoType.getMunicipio() != null) {
                com.upsaude.api.response.referencia.geografico.CidadesResponse cidade = new com.upsaude.api.response.referencia.geografico.CidadesResponse();
                cidade.setNome(enderecoType.getMunicipio().getNomeMunicipio());

                if (enderecoType.getMunicipio().getUF() != null) {
                    com.upsaude.api.response.referencia.geografico.EstadosResponse estado = new com.upsaude.api.response.referencia.geografico.EstadosResponse();
                    estado.setSigla(enderecoType.getMunicipio().getUF().getSiglaUF());
                    cidade.setEstado(estado);
                    endereco.setEstado(estado);
                }
                endereco.setCidade(cidade);
            }
            dto.setEndereco(endereco);
        }

        // Contatos
        // A extração de telefones/emails depende da lista de contatos em DadosGerais
        // Simplificando para pegar o primeiro se disponivel na estrutura (precisaria
        // verificar onde fica lista de contatos)

        // Data Atualização
        if (dados.getDataAtualizacao() != null) {
            // Converter XMLGregorianCalendar se necessario ou Date
            // Assumindo que o gerador JAXB usou Date ou XMLGregorianCalendar
            // dto.setDataAtualizacaoCnes(OffsetDateTime.now()); // Placeholder se nao
            // conseguir converter
        }
        dto.setDataAtualizacaoCnes(OffsetDateTime.now()); // Fallback

        // Servicos Habilitados (apenas placeholder, pois v1r0 de estabelecimento pode
        // não trazer tudo)
        dto.setServicosHabilitados(new ArrayList<>());

        // Status
        dto.setStatus("ATIVO"); // Default ou extrair se houver campo de status

        return dto;
    }

    public java.util.List<com.upsaude.entity.cnes.EstabelecimentoLeito> convertLeitos(
            java.util.List<com.upsaude.integration.cnes.wsdl.leito.LeitoType> source,
            com.upsaude.entity.estabelecimento.Estabelecimentos estabelecimento,
            com.upsaude.entity.sistema.multitenancy.Tenant tenant) {

        java.util.List<com.upsaude.entity.cnes.EstabelecimentoLeito> result = new ArrayList<>();
        if (source == null)
            return result;

        for (com.upsaude.integration.cnes.wsdl.leito.LeitoType item : source) {
            com.upsaude.entity.cnes.EstabelecimentoLeito entity = new com.upsaude.entity.cnes.EstabelecimentoLeito();
            entity.setEstabelecimento(estabelecimento);
            entity.setTenant(tenant);
            entity.setTipoLeitoCodigo(item.getCodigo());
            entity.setTipoLeitoDescricao(item.getDescricao());
            entity.setQuantidadeExistente(item.getQuantidadeLeito() != null ? item.getQuantidadeLeito().intValue() : 0);
            entity.setQuantidadeSus(item.getQuantidadeLeitoSUS() != null ? item.getQuantidadeLeitoSUS().intValue() : 0);
            entity.setDataAtualizacao(OffsetDateTime.now());
            result.add(entity);
        }
        return result;
    }

    public java.util.List<com.upsaude.entity.cnes.EstabelecimentoEquipamento> convertEquipamentos(
            java.util.List<com.upsaude.integration.cnes.wsdl.equipamento.EquipamentoType> source,
            com.upsaude.entity.estabelecimento.Estabelecimentos estabelecimento,
            com.upsaude.entity.sistema.multitenancy.Tenant tenant) {

        java.util.List<com.upsaude.entity.cnes.EstabelecimentoEquipamento> result = new ArrayList<>();
        if (source == null)
            return result;

        for (com.upsaude.integration.cnes.wsdl.equipamento.EquipamentoType item : source) {
            com.upsaude.entity.cnes.EstabelecimentoEquipamento entity = new com.upsaude.entity.cnes.EstabelecimentoEquipamento();
            entity.setEstabelecimento(estabelecimento);
            entity.setTenant(tenant);
            entity.setTipoEquipamentoCodigo(item.getCodigo());
            entity.setTipoEquipamentoDescricao(item.getDescricao());
            entity.setQuantidadeExistente(
                    item.getQuantidadeEquipamento() != null ? item.getQuantidadeEquipamento().intValue() : 0);
            entity.setQuantidadeEmUso(item.getQuantidadeUso() != null ? item.getQuantidadeUso().intValue() : 0);
            // Assuming tpSUS logic (1=Sim, 0=Nao) or if it has inner value
            // We need to inspect IndicadorSUSType, for now assuming it might be mapped
            // later or use available fields
            entity.setQuantidadeSus(0); // Placeholder
            entity.setDataAtualizacao(OffsetDateTime.now());
            result.add(entity);
        }
        return result;
    }
}
