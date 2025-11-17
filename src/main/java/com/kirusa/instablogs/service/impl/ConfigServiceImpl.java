package com.kirusa.instablogs.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kirusa.instablogs.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Value("${service.base.url}")
	private String serviceBaseUrl;

	@Value("${docs.url}")
	private String docsUrl;

	@Value("${fb.connect.url}")
	private String fbConnectUrl;

	@Value("${tw.connect.url}")
	private String twConnectUrl;

	@Value("${mqtt.host}")
	private String mqttHost;

	@Value("${mqtt.port}")
	private int mqttPort;

	@Value("${mqtt.user}")
	private String mqttUser;

	@Value("${mqtt.password}")
	private String mqttPassword;

	@Value("${pns.app.id}")
	private String pnsAppId;

	@Override
	public String getServiceBaseUrl() {
		return serviceBaseUrl;
	}

	@Override
	public String getDocsUrl() {
		return docsUrl;
	}

	@Override
	public String getFbConnectUrl() {
		return fbConnectUrl;
	}

	@Override
	public String getTwConnectUrl() {
		return twConnectUrl;
	}

	@Override
	public String getMqttHost() {
		return mqttHost;
	}

	@Override
	public int getMqttPort() {
		return mqttPort;
	}

	@Override
	public String getMqttUser() {
		return mqttUser;
	}

	@Override
	public String getMqttPassword() {
		return mqttPassword;
	}

	@Override
	public String getPnsAppId() {
		return pnsAppId;
	}
}
