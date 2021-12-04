package com.altimetrik.altimetrics.pojo;

public class Story {

    private String name  ;
    private String planEstimate ;
    private String scheduleState ;
    private String storyType  ;
    private String storyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanEstimate() {
        return planEstimate;
    }

    public void setPlanEstimate(String planEstimate) {
        this.planEstimate = planEstimate;
    }

    public String getScheduleState() {
        return scheduleState;
    }

    public void setScheduleState(String scheduleState) {
        this.scheduleState = scheduleState;
    }

    public String getStoryType() {
        return storyType;
    }

    public void setStoryType(String storyType) {
        this.storyType = storyType;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
}
