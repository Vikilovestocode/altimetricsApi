package com.altimetrik.altimetrics.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.response.GetResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
public class RallyServiceTest {

    @SneakyThrows
    public static void getStoriesByIterationId(String iterationId, RallyRestApi restApi  ) throws IOException {
        /// project iteration request
        GetRequest storiesListRequest = new GetRequest("/Iteration/"+iterationId+"/WorkProducts");
        GetResponse storiesListResponse = restApi.get(storiesListRequest);
        if(storiesListResponse.wasSuccessful()){
            JsonArray stories = storiesListResponse.getObject().getAsJsonObject().getAsJsonArray("Results");
            for (JsonElement story : stories) {
                JsonObject iterationObject = story.getAsJsonObject();
                String name = iterationObject.get("Name").getAsString();
                String planEstimate = iterationObject.get("PlanEstimate").toString();
                String scheduleState = iterationObject.get("ScheduleState").getAsString();
                String storyType = iterationObject.get("c_StoryType")==null ?iterationObject.get("_type").toString():iterationObject.get("c_StoryType").toString();
                String storyId = iterationObject.get("FormattedID").getAsString();
                System.out.println(name +" "+storyId + " "+ planEstimate + " "+ scheduleState);
            }
        }
    }

    @SneakyThrows
    public static void main(String[] args) throws URISyntaxException, IOException {
        RallyRestApi restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"), "_qWXWE5zFSnaScdgrPS101sLL244hokaAm37YNDiRtq8");
        restApi.setWsapiVersion("v2.0");
        restApi.setApplicationName("QueryExample");

        try {

            System.out.println("Querying for top 5 highest priority unfixed defects...");

            GetRequest allProjectsRequest = new GetRequest("/projects");
            GetResponse allProjectsResponse = restApi.get(allProjectsRequest);
            if(allProjectsResponse.wasSuccessful()){
                JsonArray projects = allProjectsResponse.getObject().getAsJsonObject().get("Results").getAsJsonArray();
                for (JsonElement project : projects) {
                    String refObjectUUID = project.getAsJsonObject().get("_refObjectUUID").getAsString();
                    String projectName = project.getAsJsonObject().get("_refObjectName").toString();
                    GetRequest getProjectIdRequest = new GetRequest("/projects/"+refObjectUUID);
                    GetResponse getProjectIdResponse = restApi.get(getProjectIdRequest);

                    if(getProjectIdResponse.wasSuccessful()){
                        String projectId = getProjectIdResponse.getObject().getAsJsonObject().get("ObjectID").getAsString();
                        /// project iteration request
                        GetRequest iterationListRequest = new GetRequest("/Projects/"+projectId+"/Iterations");
                        GetResponse iterationListResponse = restApi.get(iterationListRequest);
                        if(iterationListResponse.wasSuccessful()){
                            JsonArray iterations = iterationListResponse.getObject().get("Results").getAsJsonArray();
                            for (JsonElement iteration : iterations) {
                                JsonObject iterationObject = iteration.getAsJsonObject();
                                String startDate = iterationObject.get("StartDate").getAsString();
                                String endDate = iterationObject.get("EndDate").getAsString();
                                String sprintName = iterationObject.get("Name").getAsString();
                                String iterationId = iterationObject.get("ObjectID").getAsString();
                                System.out.println("start date : "+ startDate +" End date : "+endDate +" sprintName "+sprintName);
                                getStoriesByIterationId(iterationId,restApi);
                            }
                        }
                    }
                }
            }











































//            System.out.println("Test "+getResponse);
//            QueryRequest defectRequest = new QueryRequest("Test");
//            defectRequest.setFetch(new Fetch("Name", "FormattedID", "Description", "Iteration", "PortfolioItem"));
//            defectRequest.setPageSize(1);
//
//            defectRequest.setLimit(20);
//            defectRequest.setScopedDown(false);
//            defectRequest.setScopedUp(false);
//            defectRequest.setProject("/Project/511312762108");
//            //defectRequest.setQueryFilter(new QueryFilter("Iteration._refObjectName", "=", "Sprint_23_2022"));
//
//            QueryResponse queryResponse = restApi.query(defectRequest);
//            System.out.println(queryResponse);
////            if (queryResponse.wasSuccessful()) {
////                System.out.println(String.format("\nTotal results: %d", queryResponse.getTotalResultCount()));
////                System.out.println("Top 5:");
////                for (JsonElement result : queryResponse.getResults()) {
////                    JsonObject defect = result.getAsJsonObject();
////                    System.out.println(String.format("\t%s - %s: Priority=%s, State=%s",
////                            defect.get("FormattedID").getAsString(),
////                            defect.get("Name").getAsString(),
////                            defect.get("Priority").getAsString(),
////                            defect.get("State").getAsString()));
////                }
////            } else {
////                System.err.println("The following errors occurred: ");
////                for (String err : queryResponse.getErrors()) {
////                    System.err.println("\t" + err);
////                }
////            }
//
//            QueryRequest  releaseRequest = new QueryRequest("Release");
//            releaseRequest.setFetch(new Fetch("ReleaseStartDate", "ReleaseDate"));
//            releaseRequest.setScopedDown(false);
//            releaseRequest.setScopedUp(false);
//            releaseRequest.setProject("/Project/511312762108");
//            releaseRequest.setQueryFilter(new QueryFilter("Name", "=", "Ancestry"));
//
//            QueryResponse releaseQueryResponse = restApi.query(releaseRequest);
//            int numberOfReleasesInProject = releaseQueryResponse.getTotalResultCount();
//            System.out.println(numberOfReleasesInProject);
//            JsonObject releaseJsonObject = releaseQueryResponse.getResults().get(0).getAsJsonObject();
//            System.out.println(releaseJsonObject.get("ReleaseStartDate"));
//            System.out.println(releaseJsonObject.get("ReleaseDate"));
//
//            String rsd = releaseJsonObject.get("ReleaseStartDate").getAsString();
//            String rd = releaseJsonObject.get("ReleaseDate").getAsString();
//
//            QueryRequest  iterationRequest = new QueryRequest("Iteration");
//            iterationRequest.setFetch(new Fetch("Name","StartDate","EndDate"));
//            iterationRequest.setScopedDown(false);
//            iterationRequest.setScopedUp(false);
//            iterationRequest.setProject("/project/511312762108");
//            iterationRequest.setQueryFilter(new QueryFilter("StartDate", ">=", rsd).and(new QueryFilter("EndDate", "<=", rd)));
//
//            QueryResponse iterationQueryResponse = restApi.query(iterationRequest);
//            int numberOfIteraitons = iterationQueryResponse.getTotalResultCount();
//            System.out.println("numberOfIteraitons " + numberOfIteraitons);
//            if(numberOfIteraitons >0){
//                for (int i=0;i<numberOfIteraitons;i++){
//                    JsonObject iterationJsonObject = iterationQueryResponse.getResults().get(i).getAsJsonObject();
//                    String iterationName = iterationJsonObject.get("Name").getAsString();
//                    System.out.println("iteration: " + iterationName);
//                    QueryRequest storyRequest = new QueryRequest("HierarchicalRequirement");
//                    storyRequest.setProject("/project/511312762108");
//                    storyRequest.setFetch(new Fetch(new String[] {"Name", "FormattedID","ScheduleState"}));
//                    storyRequest.setLimit(1000);
//                    storyRequest.setScopedDown(false);
//                    storyRequest.setScopedUp(false);
//                    storyRequest.setQueryFilter(new QueryFilter("Iteration.Name", "=", iterationName));
//
//                    QueryResponse storyQueryResponse = restApi.query(storyRequest);
//                    System.out.println("Number of stories in " + iterationName + " :" + storyQueryResponse.getTotalResultCount());
//
//                    for (int j=0; j<storyQueryResponse.getResults().size();j++){
//                        JsonObject storyJsonObject = storyQueryResponse.getResults().get(j).getAsJsonObject();
//                        System.out.println("Name: " + storyJsonObject.get("Name") + " FormattedID: " + storyJsonObject.get("FormattedID") + " ScheduleState: " + storyJsonObject.get("ScheduleState"));
//                    }
//                }
//            }


        } finally {
            //Release resources
            restApi.close();
        }

    }


}

