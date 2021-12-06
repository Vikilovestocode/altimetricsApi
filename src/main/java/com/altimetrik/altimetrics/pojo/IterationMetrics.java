package com.altimetrik.altimetrics.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IterationMetrics {
    
	private String currentSprintName;
    private int acceptedStatePoints;
    private int inProgressStatePoints;
    private int completedStatePoints;
    private int definedStatePoints;
    
    private int plannedPoints;
    private int doPoints;
    
    private int lastSprintsAverage;
    private int latestSprintPoints;
    
    private float sayDoPercentage = 0.0f;
    private float velocityPercentage = 0.0f;
    
}
