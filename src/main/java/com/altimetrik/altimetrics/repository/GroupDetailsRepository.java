package com.altimetrik.altimetrics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.altimetrics.entity.GroupDetails;

@Repository
public interface GroupDetailsRepository extends JpaRepository<GroupDetails, Long> {

	Optional<GroupDetails> findByGroupName(String groupName);
	
	Optional<GroupDetails> findByRallyGroupId(String rallyGroupId);
}
