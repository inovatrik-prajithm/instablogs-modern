package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Calendar;

@Entity
@Table(name = "iv_invitation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iv_invitation_id", nullable = false, updatable = false)
	private Long ivInvitationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false)
	private Calendar creationDate = Calendar.getInstance(); // avoids null errors

	@Column(name = "by_iv_user_id", nullable = false)
	private Long byIvUserId;

	@Column(name = "invite_url", length = 2048)
	private String inviteUrl;

	@Column(name = "from_user_device_id")
	private Long fromUserDeviceId;

	@Column(name = "source_app_type", length = 10)
	private String sourceAppType;

	@Column(name = "referral_code", length = 6)
	private String referralCode;
}
