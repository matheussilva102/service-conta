package br.com.mbausp.eda.product.conta.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContaUsuarioPayload(@NotNull Long number, @NotBlank String digit, @NotBlank String idCliente) {

}
