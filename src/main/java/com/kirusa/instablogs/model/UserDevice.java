package com.kirusa.instablogs.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "iv_user_device")
public class UserDevice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ivUserDeviceId;

	@Column(name = "iv_device_id")
	private String ivDeviceId;

	@Column(name = "blogger_id")
	private Long bloggerId;

	@Column(name = "app_type")
	private String appType;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "iv_user_id")
	private Long ivUserId;
	
	@Column(name = "device_model")
	private String deviceModel;
	
	@Column(name = "os_type")
	private String osType;
	
	@Column(name = "os_ver")
	private String osVer;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "last_accessed")
	private LocalDateTime lastAccessed;
	
	@Column(name = "creation_date", nullable = false)
	private Date creationDate;
	
	@Column(name = "is_inactive", nullable = false)
	boolean isInactive = false;

}
