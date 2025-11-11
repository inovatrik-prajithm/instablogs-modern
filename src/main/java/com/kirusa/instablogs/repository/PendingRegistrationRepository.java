package com.kirusa.instablogs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirusa.instablogs.model.PendingRegistration;

public interface PendingRegistrationRepository extends JpaRepository<PendingRegistration, Long> {
	Optional<PendingRegistration> findByContactIdAndDeviceId(String contactId, String deviceId);

}
