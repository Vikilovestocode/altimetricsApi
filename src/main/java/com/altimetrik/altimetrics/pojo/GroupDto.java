package com.altimetrik.altimetrics.pojo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {

	@Builder.Default
	private Integer sno = 1;
	private String id;//rally-group-id
	private Long groupDetailsId;//primary key of group_details
	private String groupName;
	private String projectDescription;
	private String engagementType;
	private String deliveryModel;
	private String startDate;
	private String endDate;
	private String teamSize;
	private String stakeholders;
	private String engineeringManager;
	private String scrumMaster;
	private String technology;
	
	private List<Project> projects;
	
}
