package com.kirusa.instablogs.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecureKeyInfo {
	private Long bloggerId;
	private String contactId;
	private Long deviceId;
}
