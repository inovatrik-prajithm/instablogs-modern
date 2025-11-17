package com.kirusa.instablogs.service;

import java.util.List;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.model.Blogger;

public interface ContactService {

	/**
	 * Returns list of APIResponse.UserContact DTOs for the given blogger.
	 */
	List<APIResponse.UserContact> getUserContacts(Blogger blogger);
}
