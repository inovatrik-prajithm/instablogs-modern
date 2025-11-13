package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kvsms_node_network")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KvsmsNodeNetwork {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "kvsms_node_id")
	private Long kvsmsNodeId;

	@Column(name = "kvsms_network_id")
	private Long kvsmsNetworkId;

	@Column(name = "mcc_mnc")
	private String mccMnc;

	@Column(name = "network_name")
	private String networkName;
	
	@Column(name = "country_code", length = 3, nullable = false)
	String countryCode;
}
