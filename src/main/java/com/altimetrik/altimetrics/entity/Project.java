package com.altimetrik.altimetrics.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(name = "rally_project_id", nullable = false)
	private String rallyProjectId;
	
	@Column(name = "group_id", insertable = true)
	private Long groupId;

	@Column(name = "project_name")
	private String projectName;

/*	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "group_id", nullable = false)
	private GroupDetails groupDetails;*/
}
