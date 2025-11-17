package com.kirusa.instablogs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirusa.instablogs.model.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
	Optional<Invitation> findByInviteUrl(String inviteUrl);
}
