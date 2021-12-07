package com.altimetrik.altimetrics.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.altimetrik.altimetrics.entity.GroupDetails;
import com.altimetrik.altimetrics.pojo.GroupDto;
import com.altimetrik.altimetrics.pojo.ProjectGroup;
import com.altimetrik.altimetrics.repository.GroupDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GroupDetailsService {
	
	@Autowired
	private GroupDetailsRepository groupDetailsRepository;
	
	@Autowired
	private RallyService rallyService;
	
	public List<GroupDto> getAllGroupDetails() {
		List<GroupDetails> groupDetails = groupDetailsRepository.findAll();
		List<GroupDto> groupDtos = new ArrayList<>();
		IntStream.range(0, groupDetails.size()).forEach(sno -> {
			GroupDto groupDto = convertToGroupDto(groupDetails.get(sno));
			groupDto.setSno(sno+1);
			groupDtos.add(groupDto);
		});
		return groupDtos;
	}
	
	public GroupDto getGroupDetailsByName(String groupName) {
		if(!StringUtils.hasLength(groupName)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		GroupDetails groupDetails = groupDetailsRepository.findByGroupName(groupName)
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return convertToGroupDto(groupDetails);
	}
	
	public GroupDto getGroupDetailsById(String rallyGroupId) {
		GroupDetails groupDetails =  groupDetailsRepository.findByRallyGroupId(rallyGroupId)
		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return convertToGroupDto(groupDetails);
	}
	
	public GroupDto createGroupDetails(GroupDetails groupDetails) {
		if(!StringUtils.hasLength(groupDetails.getGroupName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return convertToGroupDto(groupDetailsRepository.save(groupDetails));
	}
	
	public GroupDto updateGroupDetails(String rallyGroupId, GroupDetails groupDetails) {
		if(!StringUtils.hasLength(groupDetails.getGroupName()) 
				&& !StringUtils.hasLength(rallyGroupId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		Optional<GroupDetails> existingGroupDetails = groupDetailsRepository.findByRallyGroupId(rallyGroupId);
		if(!existingGroupDetails.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		GroupDetails updatedGroupDetails = existingGroupDetails.get();
		copyGroupDetails(groupDetails, updatedGroupDetails);
		return convertToGroupDto(groupDetailsRepository.save(updatedGroupDetails));
	}
	
	private GroupDto convertToGroupDto(GroupDetails sourceGroupDetails) {
		GroupDto groupDto = GroupDto.builder().id(sourceGroupDetails.getRallyGroupId())
				.groupDetailsId(sourceGroupDetails.getId())
				.groupName(sourceGroupDetails.getGroupName())
				.projectDescription(sourceGroupDetails.getProjectDescription())
				.engagementType(sourceGroupDetails.getEngagementType())
				.deliveryModel(sourceGroupDetails.getDeliveryModel())
				.startDate(sourceGroupDetails.getStartDate())
				.endDate(sourceGroupDetails.getEndDate())
				.teamSize(sourceGroupDetails.getTeamSize())
				.stakeholders(sourceGroupDetails.getStakeholders())
				.engineeringManager(sourceGroupDetails.getEngineeringManager())
				.scrumMaster(sourceGroupDetails.getScrumMaster())
				.technology(sourceGroupDetails.getTechnology())
				.rallyGroupId(sourceGroupDetails.getRallyGroupId())
				.build();
				
		try {
			ProjectGroup projectGroup = new ProjectGroup();
			projectGroup.setGroupId(sourceGroupDetails.getRallyGroupId());
			groupDto.setProjects(rallyService.getProjectGroupChildrens(projectGroup));
		} catch (IOException e) {
			log.error("Error in calling rally service", e);
		}
		return groupDto;
	}

	private void copyGroupDetails(GroupDetails sourceGroupDetails, GroupDetails destinationGroupDetails) {
		destinationGroupDetails.setGroupName(sourceGroupDetails.getGroupName());
		destinationGroupDetails.setProjectDescription(sourceGroupDetails.getProjectDescription());
		destinationGroupDetails.setEngagementType(sourceGroupDetails.getEngagementType());
		destinationGroupDetails.setDeliveryModel(sourceGroupDetails.getDeliveryModel());
		destinationGroupDetails.setStartDate(sourceGroupDetails.getStartDate());
		destinationGroupDetails.setEndDate(sourceGroupDetails.getEndDate());
		destinationGroupDetails.setTeamSize(sourceGroupDetails.getTeamSize());
		destinationGroupDetails.setStakeholders(sourceGroupDetails.getStakeholders());
		destinationGroupDetails.setEngineeringManager(sourceGroupDetails.getEngineeringManager());
		destinationGroupDetails.setScrumMaster(sourceGroupDetails.getScrumMaster());
		destinationGroupDetails.setTechnology(sourceGroupDetails.getTechnology());
	}

}
