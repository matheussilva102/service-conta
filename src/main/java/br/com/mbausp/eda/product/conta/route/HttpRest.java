package br.com.mbausp.eda.product.conta.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import br.com.mbausp.eda.product.conta.domain.ContaUsuarioPayload;

@Component
public class HttpRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .component("platform-http")
            .bindingMode(RestBindingMode.json)
            .contextPath("/api")
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "User API")
            .apiProperty("api.version", "1.2.3")
            // and enable CORS
            .apiProperty("cors", "true");

        rest("/accounts")
	        .description("contas de usuário")
	        .get("/{id}")
	            .to(RouteEnum.DIRECT_CONSULTAR_CONTA.getRoute())
	        .post("/")
		        .type(ContaUsuarioPayload.class)
		        .to(RouteEnum.DIRECT_CRIAR_CONTA.getRoute());

    }

}
