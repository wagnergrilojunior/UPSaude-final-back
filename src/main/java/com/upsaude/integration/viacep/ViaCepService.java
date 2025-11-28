package com.upsaude.integration.viacep;

import com.upsaude.enums.TipoLogradouroEnum;
import com.upsaude.entity.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepResponse buscarPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, ViaCepResponse.class);
    }

    public Endereco toEndereco(ViaCepResponse response) {
        Endereco e = new Endereco();
        e.setCep(normalizeCep(response.getCep()));
        e.setLogradouro(response.getLogradouro());
        e.setComplemento(response.getComplemento());
        e.setBairro(response.getBairro());
        e.setCodigoIbgeMunicipio(response.getIbge());
        e.setTipoLogradouro(TipoLogradouroEnum.fromLogradouro(response.getLogradouro()));
        e.setPais("Brasil");
        return e;
    }

    private String normalizeCep(String cep) {
        if (cep == null) return null;
        return cep.replaceAll("[^0-9]", "");
    }

    
}
