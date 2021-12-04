package com.altimetrik.altimetrics.controller;

import com.altimetrik.altimetrics.pojo.Iteration;
import com.altimetrik.altimetrics.pojo.IterationMetrics;
import com.altimetrik.altimetrics.pojo.Project;
import com.altimetrik.altimetrics.pojo.Story;
import com.altimetrik.altimetrics.service.RallyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/iterations")
public class IterationController {

    @Autowired
    RallyService rallyService;

    @PostMapping()
    public List<Iteration> getAllIterations(@RequestBody Project project) throws IOException {
        return rallyService.getAllIterationByProject(project);
    }

    @GetMapping("/{iterationId}")
    public List<Story> getStoriesBySprint(@PathVariable String iterationId) throws IOException {
       return rallyService.getStoriesBySprintId(iterationId);
    }

    @GetMapping("/metrics/{iterationId}")
    public IterationMetrics getSprintMetrics(@PathVariable String iterationId) throws IOException {
        return rallyService.getIterationMetricsByIterationId(iterationId);
    }
}
