package com.altimetrik.altimetrics.service;

import com.altimetrik.altimetrics.pojo.ProjectGroup;
import com.altimetrik.altimetrics.pojo.TeamDetails;
import org.springframework.stereotype.Service;

@Service
public class TeamDetailsService {

    public TeamDetails getTeamDetailsbyProjectGroup(ProjectGroup projectGroup){
        return  new TeamDetails();
    }

    public void saveTeamDetails(ProjectGroup projectGroup){

    }
}
