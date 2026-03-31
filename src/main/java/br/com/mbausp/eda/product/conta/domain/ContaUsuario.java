package br.com.mbausp.eda.product.conta.domain;

import java.time.LocalDateTime;

import br.com.mbausp.eda.product.conta.entity.ContaStatus;

public record ContaUsuario(Integer id, Long number, String digit, String idCliente, LocalDateTime dataCriacao, boolean flAtivo, LocalDateTime dataAlteracao, ContaStatus status) {

}
