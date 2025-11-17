package com.kirusa.instablogs.service;

public interface ConfigService {

	String getServiceBaseUrl();

	String getDocsUrl();

	String getFbConnectUrl();

	String getTwConnectUrl();

	String getMqttHost();

	int getMqttPort();

	String getMqttUser();

	String getMqttPassword();

	String getPnsAppId();
}
