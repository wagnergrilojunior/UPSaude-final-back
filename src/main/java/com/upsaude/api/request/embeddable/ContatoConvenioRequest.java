package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de contato convênio")
public class ContatoConvenioRequest {
    @Size(max = 20, message = "Telefone principal deve ter no máximo 20 caracteres")
    private String telefonePrincipal;

    @Size(max = 20, message = "Telefone secundário deve ter no máximo 20 caracteres")
    private String telefoneSecundario;

    @Size(max = 20, message = "Fax deve ter no máximo 20 caracteres")
    private String fax;

    @Size(max = 20, message = "WhatsApp deve ter no máximo 20 caracteres")
    private String whatsapp;

    @Email(message = "Email principal inválido")
    @Size(max = 255, message = "Email principal deve ter no máximo 255 caracteres")
    private String emailPrincipal;

    @Email(message = "Email secundário inválido")
    @Size(max = 255, message = "Email secundário deve ter no máximo 255 caracteres")
    private String emailSecundario;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    private String site;

    @Size(max = 100, message = "Nome do contato deve ter no máximo 100 caracteres")
    private String nomeContato;

    @Size(max = 100, message = "Cargo do contato deve ter no máximo 100 caracteres")
    private String cargoContato;

    @Size(max = 20, message = "Telefone do contato deve ter no máximo 20 caracteres")
    private String telefoneContato;

    @Email(message = "Email do contato inválido")
    @Size(max = 255, message = "Email do contato deve ter no máximo 255 caracteres")
    private String emailContato;
}
