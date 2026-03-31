package br.com.mbausp.eda.product.conta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mbausp.eda.product.conta.entity.ContaClienteEntity;

public interface ContaClienteRepository extends JpaRepository<ContaClienteEntity, Long> {
	
}
