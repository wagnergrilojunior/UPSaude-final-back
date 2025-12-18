package com.upsaude.service.sistema;

import com.upsaude.api.response.sistema.EnumInfoResponse;
import com.upsaude.api.response.sistema.EnumsResponse;

public interface EnumsService {

    EnumsResponse listarTodosEnums();

    java.util.List<String> listarNomesEnums();

    EnumInfoResponse obterEnumPorNome(String nomeEnum);
}
