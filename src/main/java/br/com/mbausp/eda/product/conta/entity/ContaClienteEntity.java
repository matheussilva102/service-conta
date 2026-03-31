package br.com.mbausp.eda.product.conta.entity;

import java.time.LocalDateTime;

import br.com.mbausp.eda.product.conta.domain.ContaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta_cliente", schema = "conta")
public class ContaClienteEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@Column(name = "cliente_id", nullable = false)
    private String clienteId;

	@Column(name = "nu_conta", nullable = false)
    private Long nuConta;

	@Column(name = "digito_conta", nullable = false)
    private String digitoConta;

	@Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

	@Column(name = "conta_ativa", nullable = false)
    private Boolean contaAtiva;

	@Column(name = "data_alteracao", nullable = true)
    private LocalDateTime dataAlteracao;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
    private ContaStatus status;

	public int getId() {
		return id;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public Long getNuConta() {
		return nuConta;
	}

	public void setNuConta(Long nuConta) {
		this.nuConta = nuConta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Boolean getContaAtiva() {
		return contaAtiva;
	}

	public void setContaAtiva(Boolean contaAtiva) {
		this.contaAtiva = contaAtiva;
	}

	public LocalDateTime getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDateTime dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public ContaStatus getStatus() {
		return status;
	}

	public void setStatus(ContaStatus status) {
		this.status = status;
	}


}
