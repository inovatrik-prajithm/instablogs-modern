package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "iv_user_device")
public class UserDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long iv_user_device_id;
	
	@Column(name = "iv_device_id")
	private String ivDeviceId;

	@Column(name = "blogger_id")
	private Long bloggerId;

	@Column(name = "app_type")
	private String appType;

	@Column(name = "device_id")
	private String deviceId;

}
