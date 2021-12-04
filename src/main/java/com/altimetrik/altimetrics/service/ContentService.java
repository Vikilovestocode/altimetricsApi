package com.altimetrik.altimetrics.service;


import com.altimetrik.altimetrics.pojo.ProjectGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ContentService {

    @Autowired
    RallyService rallyService;

    public List<ProjectGroup> generatePPTContent(List<ProjectGroup> projectGroups){
        projectGroups.forEach(projectGroup -> {

            projectGroup.getProjects().forEach(project -> {
                project.getIterations().forEach(iteration -> {
                    try {
                        iteration.setStories(rallyService.getStoriesBySprintId(iteration.getIterationId()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        });
        return projectGroups;
    }
}
