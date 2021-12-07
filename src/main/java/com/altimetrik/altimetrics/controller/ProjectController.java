package com.altimetrik.altimetrics.controller;

import com.altimetrik.altimetrics.pojo.Iteration;
import com.altimetrik.altimetrics.pojo.Project;
import com.altimetrik.altimetrics.pojo.ProjectGroup;
import com.altimetrik.altimetrics.pojo.Story;
import com.altimetrik.altimetrics.service.RallyService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    RallyService rallyService ;



    @GetMapping()
    public List<ProjectGroup> getAllProjects() throws IOException {
       return rallyService.getAllProjects();
    }

    @PostMapping
    public List<Story> getProjectCurrentSprintStories(@RequestBody Project project) throws IOException {
        return rallyService.getProjectCurrentIterationStories(project);
    }

    @PostMapping(value = "/listStoriesByIds")
    public Map<String, Object> getProjectCurrentSprintStories(@RequestBody Map<String, List<String>> projectIds) throws IOException {
        Map<String, Object>  map = new HashMap<>();
        projectIds.get("projectIds").stream().forEach(projectId -> {
            Project temp = new Project();
            temp.setProjectId(projectId);
            try {
                map.put(projectId, rallyService.getProjectWithStoriesAndMetrics(temp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

         return map;
    }

//    @PostMapping("/currnet")
//    public List<Map<String, Story>> getProjectCurrentSprintStories(@RequestBody Project project) throws IOException {
//        return rallyService.getProjectCurrentIterationStories(project);
//    }






}
