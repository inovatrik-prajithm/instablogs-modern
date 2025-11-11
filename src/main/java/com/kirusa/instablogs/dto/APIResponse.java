package com.kirusa.instablogs.dto;

import java.util.Set;

import lombok.Data;

@Data
public class APIResponse {
	private String regId;
	private String regSecureKey;
	private String action;
	private boolean newIvUser;
	private boolean newReachMeUser;
	private boolean phoneNumEdited;
	private boolean userVerified;
	private Set<Object> userContacts;

}
