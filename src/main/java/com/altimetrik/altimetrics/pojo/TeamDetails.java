package com.altimetrik.altimetrics.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TeamDetails {
    private String projectDescription;
    private String engagementType;
    private String startDate;
    private String teamSize;
    private String deliveryModel;
    private String endDate;
    private String stackHolders;
    private String engMangers;
    private String scrumMaster;
    private String technology;

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getEngagementType() {
        return engagementType;
    }

    public void setEngagementType(String engagementType) {
        this.engagementType = engagementType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public String getDeliveryModel() {
        return deliveryModel;
    }

    public void setDeliveryModel(String deliveryModel) {
        this.deliveryModel = deliveryModel;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStackHolders() {
        return stackHolders;
    }

    public void setStackHolders(String stackHolders) {
        this.stackHolders = stackHolders;
    }

    public String getEngMangers() {
        return engMangers;
    }

    public void setEngMangers(String engMangers) {
        this.engMangers = engMangers;
    }

    public String getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(String scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
