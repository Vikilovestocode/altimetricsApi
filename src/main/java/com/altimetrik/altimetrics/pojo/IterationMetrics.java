package com.altimetrik.altimetrics.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class IterationMetrics {
    private int plannedPoints;
    private int Acceptedpoints;
    private int inProgresspoints;
    private int completedPoints;
    private int definedPoints;
    private int doPoints;

    public int getPlannedPoints() {
        return plannedPoints;
    }

    public void setPlannedPoints(int plannedPoints) {
        this.plannedPoints = plannedPoints;
    }

    public int getAcceptedpoints() {
        return Acceptedpoints;
    }

    public void setAcceptedpoints(int acceptedpoints) {
        Acceptedpoints = acceptedpoints;
    }

    public int getInProgresspoints() {
        return inProgresspoints;
    }

    public void setInProgresspoints(int inProgresspoints) {
        this.inProgresspoints = inProgresspoints;
    }

    public int getCompletedPoints() {
        return completedPoints;
    }

    public void setCompletedPoints(int completedPoints) {
        this.completedPoints = completedPoints;
    }

    public int getDefinedPoints() {
        return definedPoints;
    }

    public void setDefinedPoints(int definedPoints) {
        this.definedPoints = definedPoints;
    }

    public int getDoPoints() {
        return doPoints;
    }

    public void setDoPoints(int doPoints) {
        this.doPoints = doPoints;
    }
}
