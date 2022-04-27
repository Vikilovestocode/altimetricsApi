package com.altimetrik.altimetrics.service;

import com.altimetrik.altimetrics.pojo.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RallyService {

	private static final DecimalFormat df = new DecimalFormat("0.00");
	private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    @Autowired
    RallyRestApi rallyRestApi;

    public List<ProjectGroup> getAllProjects() throws IOException {
        GetRequest allProjectsRequest = new GetRequest("/projects");
        GetResponse allProjectsResponse = null;
        try {
            allProjectsResponse = rallyRestApi.get(allProjectsRequest);
            if (allProjectsResponse.wasSuccessful()) {
            JsonArray projectGroups = allProjectsResponse.getObject().getAsJsonObject().get("Results").getAsJsonArray();
            List<ProjectGroup> projectGroupList = new ArrayList<>();
            for (JsonElement p : projectGroups) {
                ProjectGroup group = new ProjectGroup();
                group.setGroupId(p.getAsJsonObject().get("_refObjectUUID").getAsString());
                group.setGroupName(p.getAsJsonObject().get("_refObjectName").getAsString());
                group.setProjects(getProjectGroupChildrens(group));
                //List<Iteration> iterations = getAllIterationByProject(project);
                //project.setIterations(iterations);
              projectGroupList.add(group);
            }

            return projectGroupList;
        }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (rallyRestApi != null) {
                rallyRestApi.close();
            }
        }
        return null;
    }

    public List<Story> getProjectCurrentIterationStories(Project project) throws IOException {
        List<Iteration> iterations = getAllIterationByProject(project);
        Optional<Iteration> currentIteration = iterations.stream().filter(i -> isCurrentIteration(i)).findFirst();
        if(currentIteration.isPresent()){
            List<Story> currentSprintStories = getStoriesBySprintId(currentIteration.get().getIterationId());
           return  currentSprintStories;
        }
        return null;
    }

    public Object getProjectWithStoriesAndMetrics(Project project) throws IOException {
        List<Iteration> iterations = getAllIterationByProject(project);
        Optional<Iteration> currentIteration = iterations.stream().filter(i -> isCurrentIteration(i)).findFirst();
        if(currentIteration.isPresent()){
            Map<String, Object> map = new HashMap<>();
            List<Story> currentSprintStories = getStoriesBySprintId(currentIteration.get().getIterationId());
            map.put("currIteration", currentIteration);
            map.put("stories", currentSprintStories);
            map.put("iterationMetrics", getMetricsByProject(project));
            return  map;
        }
        return null;
    }


    public IterationMetrics getIterationMetricsByIterationId(String iterationId) throws IOException {
        GetRequest iterationMericsRequest = new GetRequest("/iterationstatus/"+iterationId);
        GetResponse iterationMericsResponse = rallyRestApi.get(iterationMericsRequest);
        if (iterationMericsResponse.wasSuccessful()) {
            JsonObject iterationMertics = iterationMericsResponse.getObject().getAsJsonObject();
            IterationMetrics metrics = new IterationMetrics();
            metrics.setPlannedPoints(iterationMertics.get("ActualPlannedAmount").getAsInt());
            metrics.setAcceptedStatePoints(iterationMertics.get("AcceptedAmount").getAsInt());
            metrics.setCompletedStatePoints(iterationMertics.get("CompletedAmount").getAsInt());
            metrics.setDefinedStatePoints(iterationMertics.get("DefinedAmount").getAsInt());
            metrics.setInProgressStatePoints(iterationMertics.get("InProgressAmount").getAsInt());
            metrics.setDoPoints(metrics.getCompletedStatePoints()+metrics.getAcceptedStatePoints());
            return metrics;
        }
        return null;
    }
    
    public IterationMetrics getMetricsByProject(Project project) throws IOException {
    	String projectRef = "/project/"+project.getProjectId();
    	QueryRequest  iterationRequest = new QueryRequest("IterationStatus");
        iterationRequest.setFetch(new Fetch("Name","AcceptedAmount","ActualPlannedAmount","CompletedAmount","InProgressAmount","DefinedAmount"));
        iterationRequest.setScopedDown(false);
        iterationRequest.setScopedUp(false);
        iterationRequest.setProject(projectRef);
        iterationRequest.setOrder("Iteration.EndDate DESC");
        iterationRequest.setQueryFilter(new QueryFilter("Iteration.StartDate", "<=", LocalDate.now().toString()));
        
        QueryResponse iterationStatusQueryResponse = rallyRestApi.query(iterationRequest);
        IterationMetrics metrics = new IterationMetrics();
        if (iterationStatusQueryResponse.wasSuccessful()) {
        	int totalCount = iterationStatusQueryResponse.getTotalResultCount();
        	if(totalCount > 0) {
        		JsonArray iterationStatuses = iterationStatusQueryResponse.getResults();
        		JsonObject currentIterationStatus = iterationStatuses.get(0).getAsJsonObject();
        		
        		metrics.setCurrentSprintName(currentIterationStatus.get("Name").getAsString());
                metrics.setPlannedPoints(currentIterationStatus.get("ActualPlannedAmount").getAsInt());
                metrics.setAcceptedStatePoints(currentIterationStatus.get("AcceptedAmount").getAsInt());
                metrics.setCompletedStatePoints(currentIterationStatus.get("CompletedAmount").getAsInt());
                metrics.setDefinedStatePoints(currentIterationStatus.get("DefinedAmount").getAsInt());
                metrics.setInProgressStatePoints(currentIterationStatus.get("InProgressAmount").getAsInt());
                metrics.setDoPoints(metrics.getCompletedStatePoints()+metrics.getAcceptedStatePoints());
                metrics.setSayDoPercentage(Float.valueOf(df.format(getPercentage(metrics.getDoPoints(), metrics.getPlannedPoints()))));
                
                if(totalCount >= 3) {
                	metrics.setLatestSprintPoints(iterationStatuses.get(1).getAsJsonObject().get("AcceptedAmount").getAsInt());
                    int totalAcceptedPoints = 0;
                    for (int i=2;i<totalCount;i++) {
                        JsonObject iterationStatusObject = iterationStatuses.get(i).getAsJsonObject();
                        totalAcceptedPoints += iterationStatusObject.get("AcceptedAmount").getAsInt();
                    }
                    metrics.setLastSprintsAverage(Integer.divideUnsigned(totalAcceptedPoints, totalCount-2));
                    metrics.setVelocityPercentage(Float.valueOf(df.format(getPercentage(metrics.getLatestSprintPoints(), metrics.getLastSprintsAverage()))));
                }
        	}
        }
        return metrics;
    }
    
    private Float getPercentage(int base, int percent) {
    	if(base != 0 && percent != 0) {
    		return (base * 100.0f) / percent;
    	}
    	
    	return 0.0f;
    }
    
    public boolean isCurrentIteration(Iteration iteration){

        LocalDate startDate = LocalDate.parse(iteration.getStartDate(), inputFormatter);
        LocalDate endDate = LocalDate.parse(iteration.getEndDate(), inputFormatter);
        if(!LocalDate.now().isBefore(startDate) && !LocalDate.now().isAfter(endDate)){
            return true;
        }
    return false;
    }
//
//    public Project getProjectByName(String projectName) throws IOException {
//        Optional<Project> project = getAllProjects().stream().filter(p -> p.getProjectName().equalsIgnoreCase(projectName)).findFirst();
//        if (project.isPresent()) {
//            return project.get();
//        }
//        return null;
//    }

    public List<Iteration> getAllIterationByProject(Project project) throws IOException {
        GetRequest getProjectIdRequest = new GetRequest("/projects/" + project.getProjectId());
        GetResponse getProjectIdResponse = rallyRestApi.get(getProjectIdRequest);
        List<Iteration> iterationList = new ArrayList<>();
        System.out.println(" getProjectIdResponse "+getProjectIdResponse.wasSuccessful());
        if (getProjectIdResponse.wasSuccessful()) {
            String projectObjectId = getProjectIdResponse.getObject().getAsJsonObject().get("ObjectID").getAsString();

            GetRequest iterationListRequest = new GetRequest("/Projects/" + projectObjectId + "/Iterations");
            iterationListRequest.addParam("pagesize", Integer.MAX_VALUE+"");
            GetResponse iterationListResponse = rallyRestApi.get(iterationListRequest);
            if (iterationListResponse.wasSuccessful()) {
                JsonArray iterations = iterationListResponse.getObject().get("Results").getAsJsonArray();

                for (JsonElement iteration : iterations) {
                    JsonObject iterationObject = iteration.getAsJsonObject();
                    Iteration it = new Iteration();
                    it.setStartDate(iterationObject.get("StartDate").getAsString());
                    it.setEndDate(iterationObject.get("EndDate").getAsString());
                    it.setSprintName(iterationObject.get("Name").getAsString());
                    it.setIterationId(iterationObject.get("ObjectID").getAsString());
                    //it.setStories(getStoriesBySprintId(it.getIterationId()));
                    iterationList.add(it);

                }
            }
        }
        return iterationList;
    }

    public List<Story> getStoriesBySprintId(String iterationId) throws IOException {

        /// project iteration request
        GetRequest storiesListRequest = new GetRequest("/Iteration/"+iterationId+"/WorkProducts");
        GetResponse storiesListResponse = rallyRestApi.get(storiesListRequest);
        List<Story> storyList = new ArrayList<>();

        if(storiesListResponse.wasSuccessful()){
            JsonArray stories = storiesListResponse.getObject().getAsJsonObject().getAsJsonArray("Results");
            for (JsonElement story : stories) {
                JsonObject iterationObject = story.getAsJsonObject();
                Story s = new Story();
                s.setName(iterationObject.has("Name")? iterationObject.get("Name").getAsString(): "");
                s.setPlanEstimate(iterationObject.has("PlanEstimate")?iterationObject.get("PlanEstimate").toString(): "");
                s.setScheduleState(iterationObject.has("ScheduleState")?iterationObject.get("ScheduleState").getAsString(): "");
                s.setStoryId(iterationObject.has("FormattedID")?iterationObject.get("FormattedID").getAsString(): "");
                String storyType = "";
/*                if(iterationObject.has("c_StoryType")){
                    storyType = iterationObject.get("c_StoryType").getAsString();
                } else {
                    if(iterationObject.has("_type")){
                        storyType = iterationObject.get("_type").getAsString();
                    }
                }*/

//                String storyType = iterationObject.get("c_StoryType")==null ?iterationObject.get("_type").getAsString():iterationObject.get("c_StoryType").getAsString();
                s.setStoryType(storyType);
                storyList.add(s);
            }
        }
        return storyList;
    }

    public List<Project> getProjectGroupChildrens(ProjectGroup projectGroup) throws IOException {
        GetRequest getChildrensRequest = new GetRequest("/Project/"+projectGroup.getGroupId()+"/Children");
        GetResponse getAllChildrensResponse = rallyRestApi.get(getChildrensRequest);
        List<Project> childrenProjectList = new ArrayList<>();
        if (getAllChildrensResponse.wasSuccessful()) {
            JsonArray projects = getAllChildrensResponse.getObject().getAsJsonObject().get("Results").getAsJsonArray();

            for (JsonElement p : projects) {
                Project newProject = new Project();
                newProject.setProjectId(p.getAsJsonObject().get("_refObjectUUID").getAsString());
                newProject.setProjectName(p.getAsJsonObject().get("_refObjectName").getAsString());
                /// to get the project description details
                GetRequest getProjectIdRequest = new GetRequest("/projects/" + projectGroup.getGroupId());
                GetResponse getProjectIdResponse = rallyRestApi.get(getProjectIdRequest);
                if(getProjectIdResponse.wasSuccessful()){
                    String description = getProjectIdResponse.getObject().getAsJsonObject().get("Description").getAsString();
                    String creationDate = getProjectIdResponse.getObject().getAsJsonObject().get("CreationDate").getAsString();
//                    System.out.println("Project ID "+ project.getProjectId());
//                    String owner = getProjectIdResponse.getObject().getAsJsonObject().get("Owner").isJsonNull() ?
//                            " ":getProjectIdResponse.getObject().getAsJsonObject().get("Owner").getAsJsonObject().get("_refObjectName").getAsString();
//                    String teamSize = getProjectIdResponse.getObject().getAsJsonObject().get("TeamMembers").isJsonNull() ?
//                            " ":getProjectIdResponse.getObject().getAsJsonObject().get("TeamMembers").getAsJsonObject().get("Count").getAsString();
                    //newProject.setDescription(description);
                   // newProject.setCreationDate(creationDate);
                    // newProject.setOwner(owner);
                    //newProject.setTeamSize(teamSize);
                }
                childrenProjectList.add(newProject);
            }
        }
        return childrenProjectList;
    }

}