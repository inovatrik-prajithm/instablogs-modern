package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "vb_blogger")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blogger implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blogger_id", nullable = false, unique = true)
	private Long bloggerId;

	@Column(name = "blogger_type", length = 1, nullable = false)
	private String bloggerType;

	@Column(name = "login_id", length = 200)
	private String loginId;

	@Column(name = "first_name", length = 200)
	private String firstName;

	@Column(name = "last_name", length = 200)
	private String lastName;

	@Column(name = "display_name", length = 200)
	private String displayName;

	@Column(name = "email", length = 200)
	private String email;

	@Column(name = "country_code", length = 10)
	private String countryCode;

	@Column(name = "primary_number", length = 45)
	private String primaryNumber;

	@Column(name = "followers_cnt")
	private Integer followersCnt;

	@Column(name = "followings_cnt")
	private Integer followingsCnt;

	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "status_remark", length = 200)
	private String statusRemark;

	@Column(name = "profile_picture_name", length = 255)
	private String profilePictureName;

	@Column(name = "date_of_birth")
	private java.util.Date dateOfBirth;

	@Column(name = "creation_date")
	private java.util.Date creationDate;

	@Column(name = "last_accessed")
	private java.util.Date lastAccessed;

	@Column(name = "locale_preference", length = 20)
	private String localePreference;

	@Column(name = "public_profile")
	private Boolean publicProfile;

	@Column(name = "send_notifications")
	private Boolean sendNotifications;

	@Column(name = "voice_vobolo_sent_cnt")
	private Integer voiceVoboloSentCnt;

	@Column(name = "text_vobolo_sent_cnt")
	private Integer textVoboloSentCnt;

	@Column(name = "colleague_group_allowed")
	private Boolean colleagueGroupAllowed;

	@Column(name = "family_group_allowed")
	private Boolean familyGroupAllowed;

	@Column(name = "friend_group_allowed")
	private Boolean friendGroupAllowed;

	@Column(name = "confirm_follower")
	private Boolean confirmFollower;

	@Column(name = "fb_user_id", length = 200)
	private String fbUserId;

	@Column(name = "pin", length = 20)
	private String pin;

	@Column(name = "last_blog_id")
	private Long lastBlogId;

	@Column(name = "iv_device_cnt")
	@Builder.Default
	private Integer deviceCnt = 0;

	@Column(name = "terms_accepted")
	private Boolean termsAccepted;

	@Column(name = "is_iv_user")
	private Boolean isIVUser;

	@Column(name = "is_ring_user")
	private Boolean isRingUser;

	@Column(name = "is_blog_user")
	private Boolean isBlogUser;

	@Column(name = "is_reachme_user")
	private Boolean isReachMeUser;

	@Column(name = "iv_signup_date")
	private java.util.Date ivSignupDate;

	@Column(name = "created_at")
	private java.util.Date createdAt;

	@Column(name = "updated_at")
	private java.util.Date updatedAt;

	@Column(name = "source_app_type")
	private String sourceAppType;

	@Column(name = "source_app")
	private String sourceApp;

	@Column(name = "updated_date")
	private java.util.Date updatedDate;

	@Column(name = "kvsms_node_id")
	private Integer kvsmsNodeId;

	@Column(name = "kvsms_network_id")
	private Long kvsmsNetworkId;

	@Column(name = "is_profile_pic_set")
	private Boolean isProfilePicSet;

	@Column(name = "profile_pic_uri")
	private String profilePicUri;

	@Column(name = "thumbnail_profile_pic_uri")
	private String thumbnailProfilePicUri;

	@Column(name = "facebook_connection")
	private Boolean facebookConnection;

	@Column(name = "twitter_connection")
	private Boolean twitterConnection;

	@Column(name = "fb_post_enabled")
	private Boolean fbPostEnabled;

	@Column(name = "tw_post_enabled")
	private Boolean twPostEnabled;
	
	@Column(name = "referral_code", length = 6)
	private String inviteRefCode;

}
