package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de contato do convênio.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoConvenio {

    @Size(max = 20, message = "Telefone principal deve ter no máximo 20 caracteres")
    @Column(name = "telefone_principal", length = 20)
    private String telefonePrincipal;

    @Size(max = 20, message = "Telefone secundário deve ter no máximo 20 caracteres")
    @Column(name = "telefone_secundario", length = 20)
    private String telefoneSecundario;

    @Size(max = 20, message = "Fax deve ter no máximo 20 caracteres")
    @Column(name = "fax", length = 20)
    private String fax;

    @Size(max = 20, message = "WhatsApp deve ter no máximo 20 caracteres")
    @Column(name = "whatsapp", length = 20)
    private String whatsapp;

    @Email(message = "Email principal inválido")
    @Size(max = 255, message = "Email principal deve ter no máximo 255 caracteres")
    @Column(name = "email_principal", length = 255)
    private String emailPrincipal;

    @Email(message = "Email secundário inválido")
    @Size(max = 255, message = "Email secundário deve ter no máximo 255 caracteres")
    @Column(name = "email_secundario", length = 255)
    private String emailSecundario;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @Column(name = "site", length = 255)
    private String site;

    @Size(max = 100, message = "Nome do contato deve ter no máximo 100 caracteres")
    @Column(name = "nome_contato", length = 100)
    private String nomeContato;

    @Size(max = 100, message = "Cargo do contato deve ter no máximo 100 caracteres")
    @Column(name = "cargo_contato", length = 100)
    private String cargoContato;

    @Size(max = 20, message = "Telefone do contato deve ter no máximo 20 caracteres")
    @Column(name = "telefone_contato", length = 20)
    private String telefoneContato;

    @Email(message = "Email do contato inválido")
    @Size(max = 255, message = "Email do contato deve ter no máximo 255 caracteres")
    @Column(name = "email_contato", length = 255)
    private String emailContato;
}

