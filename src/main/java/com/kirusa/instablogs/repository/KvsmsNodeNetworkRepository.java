package com.kirusa.instablogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kirusa.instablogs.model.KvsmsNodeNetwork;

public interface KvsmsNodeNetworkRepository extends JpaRepository<KvsmsNodeNetwork, Long> {
	KvsmsNodeNetwork findByMccMnc(String mccMnc);
}
