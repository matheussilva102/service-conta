package br.com.mbausp.eda.product.conta.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "runtime.props")
@RefreshScope
public class PropertiesConfig {

	private boolean unavailable;

	private long latencyInMilli;

	public boolean isUnavailable() {
		return this.unavailable;
	}

	public void setUnavailable(boolean unavailable) {
		this.unavailable = unavailable;
	}

	public long getLatencyInMilli() {
		return this.latencyInMilli;
	}

	public void setLatencyInMilli(long latencyInMilli) {
		this.latencyInMilli = latencyInMilli;
	}


}
