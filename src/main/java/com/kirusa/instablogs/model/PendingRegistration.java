package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vb_pending_registration")
public class PendingRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regId;

    private String contactId;
    private String contactType;
    private String deviceId;
    private String pin;
    private String encryptedPin;
    private String encryptedPwd;
    private String countryCode;
    private String kvsmsNodeId;
    private String kvsmsNetworkId;
    private String osType;
    private String osVer;
    private String osBuildNum;
    private String osKernalVer;
    private String deviceModel;
    private String deviceMacAddr;
    private String deviceResolution;
    private String deviceDensity;
    private String ivClientVer;
    private Boolean isVerified;
    private Boolean phoneNumEdited;
    private Boolean oprInfoEdited;
    private String simOperator;
    private String simOperatorNm;
    private String simNetworkOperator;
    private String simNetworkOperatorNm;
    private String simSerialNumber;
    private String loginId;
    private String displayName;
    private String appType;
    private String regSecureKey;
    @Column(name = "is_sign_in", nullable = false)
    private Boolean signIn = false;
    private Integer attempts;
    private LocalDateTime regDate;
    private LocalDateTime pinExpiry;
    private String sourceApp;
    private Integer locationCityId;
    private Integer locationStateId;
	@Column(name = "emei_meid_esn", length = 20)
    private String emeiMeidEsn;
	private String pinSentBy;

}
