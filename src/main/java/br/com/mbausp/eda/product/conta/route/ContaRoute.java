package br.com.mbausp.eda.product.conta.route;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import br.com.mbausp.eda.product.conta.config.PropertiesConfig;
import br.com.mbausp.eda.product.conta.domain.ContaUsuario;
import br.com.mbausp.eda.product.conta.domain.ContaUsuarioPayload;
import br.com.mbausp.eda.product.conta.entity.ContaClienteEntity;
import br.com.mbausp.eda.product.conta.entity.ContaStatus;
import br.com.mbausp.eda.product.conta.exception.NotFoundException;
import br.com.mbausp.eda.product.conta.exception.UnavailableException;
import br.com.mbausp.eda.product.conta.repository.ContaClienteRepository;

@Component
public class ContaRoute extends RouteBuilder {

	private static final int HTTP_ERROR_CODE_400 = 400;

	private static final int HTTP_ERROR_CODE_404 = 404;

	private static final int HTTP_ERROR_CODE_503 = 503;

	private final ContaClienteRepository contaClienteRepository;

	private final PropertiesConfig props;

    public ContaRoute(ContaClienteRepository contaUsuarioRepository, PropertiesConfig props) {
    	this.contaClienteRepository = contaUsuarioRepository;
    	this.props = props;
	}

	@Override
    public void configure() throws Exception {

		onException(NotFoundException.class)
    	.id("on_exception_id")
    		.handled(true)
    		.setBody(ex -> {
    			return Map.of("message", "conta não encontrada");
    		})
    		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_ERROR_CODE_404))
    	.end();

		onException(UnavailableException.class)
    	.id("on_exception_un_id")
    		.handled(true)
    		.setBody(ex -> {
    			return Map.of("message", "serviço indisponivel");
    		})
    		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_ERROR_CODE_503))
    	.end();

    	onException(ValidationException.class)
	        .handled(true)
        	.setBody(ex -> {
    			return Map.of("message", "verifique os campos obrigatórios");
	        })
        	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HTTP_ERROR_CODE_400))
        .end();

        from(RouteEnum.DIRECT_CONSULTAR_CONTA.getRoute())
        .routeId(RouteEnum.DIRECT_CONSULTAR_CONTA.getRouteId())
	        .choice()
	        	.when(exchange -> ContaRoute.this.props.isUnavailable())
	        	.throwException(new UnavailableException())
	        .end()
	        .setBody(ex -> {
	        	var id = ex.getIn().getHeader("id", Long.class);
	        	var entity = this.contaClienteRepository.findById(id).orElseThrow(() -> new NotFoundException());
	        	return new ContaUsuario(entity.getId(),
	        			entity.getNuConta(),
	        			entity.getDigitoConta(),
	        			entity.getClienteId(),
	        			entity.getDataCriacao(),
	        			entity.getContaAtiva(),
	        			entity.getDataAlteracao(),
	        			entity.getStatus());
	        })
	        .process(ex -> {
	        	// simular latencia
	        	Thread.sleep(ContaRoute.this.props.getLatencyInMilli());
	        })
        .end();

        from(RouteEnum.DIRECT_CRIAR_CONTA.getRoute())
        .routeId(RouteEnum.DIRECT_CRIAR_CONTA.getRouteId())
        	.to("bean-validator://contaValidator")
	        .setBody(ex -> {
	        	var input = ex.getIn().getBody(ContaUsuarioPayload.class);
	        	var sEntity = new ContaClienteEntity();
	        	sEntity.setClienteId(input.idCliente());
	        	sEntity.setDigitoConta(input.digit());
	        	sEntity.setNuConta(input.number());
	        	sEntity.setDataCriacao(LocalDateTime.now());
	        	sEntity.setContaAtiva(true);
	        	sEntity.setStatus(ContaStatus.ATIVA);
	        	var rEntity = this.contaClienteRepository.save(sEntity);
	        	return new ContaUsuario(rEntity.getId(),
	        			rEntity.getNuConta(),
	        			rEntity.getDigitoConta(),
	        			rEntity.getClienteId(),
	        			rEntity.getDataCriacao(),
	        			rEntity.getContaAtiva(),
	        			rEntity.getDataAlteracao(),
	        			rEntity.getStatus());
	        })
	        .setProperty("result", body())
	        .to(RouteEnum.KAFKA_NOTIFICAR_CONTA.getRoute())
	        	.id("to_kafka_notificar_conta_id")
	        .setBody(exchangeProperty("result"))
        .end();

        from(RouteEnum.KAFKA_NOTIFICAR_CONTA.getRoute())
        .routeId(RouteEnum.KAFKA_NOTIFICAR_CONTA.getRouteId())
        	.log("Consumed message from Kafka topic TestLog: ${body}");

    }

}
