package com.kirusa.instablogs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class APIRequest {

	@JsonProperty("app_secure_key")
	private String appSecureKey;

	@JsonProperty("client_os")
	private String clientOs;

	@JsonProperty("client_os_ver")
	private String clientOsVer;

	@JsonProperty("client_app_ver")
	private String clientAppVer;

	@JsonProperty("api_ver")
	private String apiVer;

	@JsonProperty("device_id")
	private String deviceId;

	@JsonProperty("device_model")
	private String deviceModel;

	@JsonProperty("device_mac_addr")
	private String deviceMacAddr;

	@JsonProperty("device_resolution")
	private String deviceResolution;

	@JsonProperty("device_density")
	private String deviceDensity;

	@JsonProperty("imei_meid_esn")
	private String imeiMeidEsn;

	@JsonProperty("opr_info_edited")
	private Boolean oprInfoEdited;

	@JsonProperty("sim_opr_mcc_mnc")
	private String simOprMccMnc;

	@JsonProperty("sim_network_opr_mcc_mnc")
	private String simNetworkOprMccMnc;

	@JsonProperty("sim_network_opr_nm")
	private String simNetworkOprNm;

	@JsonProperty("sim_opr_nm")
	private String simOprNm;

	@JsonProperty("sim_serial_num")
	private String simSerialNum;

	@JsonProperty("phone_num")
	private String contactId;

	@JsonProperty("phone_num_edited")
	private Boolean phoneNumEdited;

	@JsonProperty("country_code")
	private String countryCode;
}
