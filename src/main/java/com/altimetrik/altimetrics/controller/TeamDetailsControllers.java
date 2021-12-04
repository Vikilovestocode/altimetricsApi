package com.altimetrik.altimetrics.controller;

import com.altimetrik.altimetrics.pojo.ProjectGroup;
import com.altimetrik.altimetrics.pojo.TeamDetails;
import com.altimetrik.altimetrics.service.TeamDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/teamdetails")
public class TeamDetailsControllers {

    @Autowired
    TeamDetailsService teamDetailsService;

    @GetMapping
    public TeamDetails getTeamDetailsByGroup(@RequestBody ProjectGroup projectGroup){
        return  teamDetailsService.getTeamDetailsbyProjectGroup(projectGroup);
    }

    @PostMapping
    public void saveTeamDetails(@RequestBody ProjectGroup projectGroup){
        teamDetailsService.saveTeamDetails(projectGroup);
    }
}
