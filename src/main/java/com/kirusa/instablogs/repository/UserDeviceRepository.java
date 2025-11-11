package com.kirusa.instablogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kirusa.instablogs.model.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    // Check if any Ring device exists for a blogger
    @Query("SELECT CASE WHEN COUNT(ud) > 0 THEN true ELSE false END " +
           "FROM UserDevice ud WHERE ud.bloggerId = :bloggerId AND ud.appType = 'RING'")
    boolean anyRingDeviceExists(@Param("bloggerId") Long bloggerId);

    // Check if any Blogs device exists for a blogger
    @Query("SELECT CASE WHEN COUNT(ud) > 0 THEN true ELSE false END " +
           "FROM UserDevice ud WHERE ud.bloggerId = :bloggerId AND ud.appType = 'BLOGS'")
    boolean anyBlogsDeviceExists(@Param("bloggerId") Long bloggerId);

    // Check if any ReachMe device exists for a blogger
    @Query("SELECT CASE WHEN COUNT(ud) > 0 THEN true ELSE false END " +
           "FROM UserDevice ud WHERE ud.bloggerId = :bloggerId AND ud.appType = 'REACHME'")
    boolean anyReachMeDeviceExists(@Param("bloggerId") Long bloggerId);

}
