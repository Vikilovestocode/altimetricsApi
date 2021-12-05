package com.altimetrik.altimetrics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "group_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(name = "group_name", unique = true, nullable = false)
	private String groupName;
	
	@NonNull
	@Column(name = "rally_group_id", unique = true, nullable = false)
	private String rallyGroupId;
	
	@Column(name = "project_description", length = 500)
	private String projectDescription;
	
	@Column(name = "engagement_type")
	private String engagementType;
	
	@Column(name = "delivery_model")
	private String deliveryModel;
	
	@Column(name = "start_date")
	private String startDate;
	
	@Column(name = "end_date")
	private String endDate;
	
	@Column(name = "team_size")
	private String teamSize;
	
	@Column(name = "stakeholders")
	private String stakeholders;
	
	@Column(name = "engineering_manager")
	private String engineeringManager;
	
	@Column(name = "scrum_master")
	private String scrumMaster;
	
	@Column(name = "technology")
	private String technology;
}
