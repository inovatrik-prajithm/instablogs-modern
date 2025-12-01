package com.kirusa.instablogs.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response DTO for all Kirusa APIs (including join_user). Matches
 * legacy InstaVoice response structure.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class APIResponse {

	@JsonProperty("status")
	private String status;

	@JsonProperty("reg_id")
	private String regId;

	@JsonProperty("iv_user_id")
	private Long ivUserId;

	@JsonProperty("iv_user_device_id")
	private Long ivUserDeviceId;

	@JsonProperty("screen_name")
	private String screenName;

	@JsonProperty("country_isd")
	private String countryIsd;

	@JsonProperty("phone_len")
	private Integer phoneLen;

	@JsonProperty("mqtt_host_name")
	private String mqttHostName;

	@JsonProperty("mqtt_port_ssl")
	private Integer mqttPortSsl;

	@JsonProperty("mqtt_user")
	private String mqttUser;

	@JsonProperty("mqtt_password")
	private String mqttPassword;

	@JsonProperty("reg_secure_key")
	private String regSecureKey;

	@JsonProperty("user_secure_key")
	private String userSecureKey;

	@JsonProperty("login_id")
	private String loginId;

	@JsonProperty("invite_url")
	private String inviteUrl;

	@JsonProperty("inv_ref_code")
	private String invRefCode;

	@JsonProperty("action")
	private String action;

	@JsonProperty("new_user")
	private boolean newIvUser;

	@JsonProperty("new_rm_user")
	private boolean newReachMeUser;

	@JsonProperty("phone_num_edited")
	private boolean phoneNumEdited;

	@JsonProperty("user_verified")
	private boolean userVerified;

	// Default configuration / metadata
	@JsonProperty("pns_app_id")
	private String pnsAppId;

	@JsonProperty("docs_url")
	private String docsUrl;

	@JsonProperty("fb_connect_url")
	private String fbConnectUrl;

	@JsonProperty("tw_connect_url")
	private String twConnectUrl;

	@JsonProperty("obd_timegap_sec")
	private int obdTimegapSec;

	@JsonProperty("ring_expiry_min")
	private int ringExpiryMin;

	@JsonProperty("chat_hostname")
	private String chatHostname;

	@JsonProperty("chat_port_ssl")
	private String chatPortSsl;

	@JsonProperty("chat_user")
	private String chatUser;

	@JsonProperty("chat_password")
	private String chatPassword;

	@JsonProperty("is_profilePic_set")
	private Boolean isProfilePicSet;

	@JsonProperty("profile_pic_uri")
	private String profilePicUri;

	@JsonProperty("thumbnail_profilePic_uri")
	private String thumbnailProfilePicUri;

	@JsonProperty("facebook_connection")
	private Boolean facebookConnection;

	@JsonProperty("twitter_connection")
	private Boolean twitterConnection;

	@JsonProperty("fb_connected")
	private Boolean fbConnected;

	@JsonProperty("tw_connected")
	private Boolean twConnected;

	@JsonProperty("fb_post_enabled")
	private Boolean fbPostEnabled;

	@JsonProperty("tw_post_enabled")
	private Boolean twPostEnabled;

	@JsonProperty("vsms_allowed")
	private Boolean vsmsAllowed;

	@JsonProperty("last_fetched_msg_id")
	private Integer lastFetchedMsgId;

	@JsonProperty("last_fetched_contact_trno")
	private Integer lastFetchedContactTrno;

	@JsonProperty("last_fetched_profile_trno")
	private Integer lastFetchedProfileTrno;

	@JsonProperty("send_email_for_iv")
	private Boolean sendEmailForIv;

	@JsonProperty("send_sms_for_iv")
	private Boolean sendSmsForIv;

	@JsonProperty("send_email_for_vb")
	private Boolean sendEmailForVb;

	@JsonProperty("send_sms_for_vb")
	private Boolean sendSmsForVb;

	@JsonProperty("send_email_for_vsms")
	private Boolean sendEmailForVsms;

	@JsonProperty("send_sms_for_vsms")
	private Boolean sendSmsForVsms;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("about_me")
	private String aboutMe;

	@JsonProperty("display_name")
	private String displayName;

	@JsonProperty("blogger_type")
	private String bloggerType;

	@JsonProperty("followers_cnt")
	private Integer followersCnt;

	@JsonProperty("public_profile")
	private Boolean publicProfile;

	@JsonProperty("followings_cnt")
	private Integer followingsCnt;

	// User contact info
	@JsonProperty("user_contacts")
	private List<UserContact> userContacts;

	// ------- Category Response (NEW) --------
	@JsonProperty("category_list")
	private List<CategoryItem> categoryList;
	
    
    @JsonProperty("language_list")
    private List<LanguageItem> languageList;

	/**
	 * Inner static class representing each user contact entry.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@JsonInclude(Include.NON_NULL)
	public static class UserContact {

		@JsonProperty("contact_id")
		private String contactId;

		@JsonProperty("contact_type")
		private String contactType;

		@JsonProperty("country_code")
		private String countryCode;

		@JsonProperty("blogger_id")
		private Long bloggerId;

		@JsonProperty("is_primary")
		private boolean isPrimary;

		@JsonProperty("status")
		private String status;

		@JsonProperty("is_virtual")
		private boolean isVirtual;

		@JsonProperty("login_id")
		private String loginId;

	}

	/** DTO for categories */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@JsonInclude(Include.NON_NULL)
	public static class CategoryItem {

		@JsonProperty("category_id")
		private Integer categoryId;

		@JsonProperty("category_desc")
		private String categoryDesc;
	}
	
	// ======================= LANGUAGE ITEM ===========================
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class LanguageItem {

	    @JsonProperty("country_code")
	    private Integer countryCode;

	    @JsonProperty("country_name")
	    private String countryName;

	    @JsonProperty("lang_code")
	    private String langCode;

	    @JsonProperty("lang_name")
	    private String langName;

	    @JsonProperty("lang_data")
	    private Map<String, String> langData;
	}

}
