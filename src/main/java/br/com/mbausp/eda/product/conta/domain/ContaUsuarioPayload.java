package br.com.mbausp.eda.product.conta.domain;

import jakarta.validation.constraints.NotNull;

public record ContaUsuarioPayload(@NotNull Long number, String digit, String idCliente) {

}
