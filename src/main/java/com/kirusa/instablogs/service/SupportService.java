package com.kirusa.instablogs.service;

import org.springframework.stereotype.Service;

@Service
public class SupportService {

	public String getSupportContactsJson() {
		// Return legacy JSON directly
		return "[{\"support_catg_id\":\"IVSupport\",\"support_catg\":\"Help\",\"show_as_iv_user\":true,"
				+ "\"iv_user_id\":\"2624836\",\"phone\":\"912222222222\",\"email\":\"support@kirusa.com\","
				+ "\"profile_pic_uri\":\"http://iv.vobolo.com/vobolo/profile-images/2222/2624836_support.jpg\","
				+ "\"thumbnail_profile_pic_uri\":\"http://iv.vobolo.com/vobolo/thumbnails/2222/2624836_support.jpg\","
				+ "\"support_send_iv\":true,\"support_send_sms\":false,\"support_send_email\":false},"
				+ "{\"feedback_catg_id\":\"IVFeedback\",\"feedback_catg\":\"Suggestions\",\"show_as_iv_user\":true,"
				+ "\"iv_user_id\":\"2624835\",\"phone\":\"911111111111\",\"email\":\"feedback@kirusa.com\","
				+ "\"profile_pic_uri\":\"http://iv.vobolo.com/vobolo/profile-images/1111/2624835_feedback.jpg\","
				+ "\"thumbnail_profile_pic_uri\":\"http://iv.vobolo.com/vobolo/thumbnails/1111/2624835_feedback.jpg\","
				+ "\"feedback_send_iv\":true,\"feedback_send_sms\":false,\"feedback_send_email\":false}]";
	}
}
