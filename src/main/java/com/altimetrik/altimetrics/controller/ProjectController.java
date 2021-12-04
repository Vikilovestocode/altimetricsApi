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
import java.util.List;

@RestController()
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    RallyService rallyService ;

    @GetMapping()
    public List<ProjectGroup> getAllProjects() throws IOException {
//        for (int i = 0; i < 10; i++) {
//            rallyService.getAllProjects();
//        }
       return rallyService.getAllProjects();
    }

    @PostMapping
    public List<Story> getProjectCurrentSprintStories(@RequestBody Project project) throws IOException {
        return rallyService.getProjectCurrentIterationStories(project);
    }






}
