package com.kirusa.instablogs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "service")
@Data
public class AppConfig {

	private String url; // maps to service.url in properties

	/**
	 * Returns the base service URL with a trailing slash.
	 */
	public String getServiceBaseURL() {
		return url.endsWith("/") ? url : url + "/";
	}
}
