package com.kirusa.instablogs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response DTO for all Kirusa APIs (including join_user).
 * Matches legacy InstaVoice response structure.
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

    @JsonProperty("reg_secure_key")
    private String regSecureKey;

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

    // User contact info
    @JsonProperty("user_contacts")
    private List<UserContact> userContacts;

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
    }
}
