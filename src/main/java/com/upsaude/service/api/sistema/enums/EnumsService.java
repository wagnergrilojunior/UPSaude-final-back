package com.upsaude.service.api.sistema.enums;

import com.upsaude.api.response.sistema.enums.EnumInfoResponse;
import com.upsaude.api.response.sistema.EnumsResponse;

public interface EnumsService {

    EnumsResponse listarTodosEnums();

    java.util.List<String> listarNomesEnums();

    EnumInfoResponse obterEnumPorNome(String nomeEnum);
}
