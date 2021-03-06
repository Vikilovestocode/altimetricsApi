package com.altimetrik.altimetrics.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

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


	@OneToMany(fetch = FetchType.EAGER,
			cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name ="group_id", referencedColumnName = "id")
	private List<Project> projects;

	@NonNull
	@Column(name = "group_name", unique = true, nullable = false)
	private String groupName;
	
	@Column(name = "rally_group_id", nullable = true)
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

	@Column(name = "group_display_name")
	private String groupNameForDisplay;

}
