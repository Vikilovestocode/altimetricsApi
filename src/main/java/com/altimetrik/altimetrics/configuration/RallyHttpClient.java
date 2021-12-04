//package com.altimetrik.altimetrics.configuration;
//
//import com.google.gson.JsonArray;
//import com.rallydev.rest.client.ApiKeyClient;
//import com.rallydev.rest.client.BasicAuthClient;
//import com.rallydev.rest.client.HttpClient;
//import com.rallydev.rest.request.*;
//import com.rallydev.rest.response.*;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.springframework.stereotype.Component;
//
//import java.io.Closeable;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@Component
//public class RallyHttpClient implements Closeable {
//    protected HttpClient client;
//
//    /** @deprecated */
//    public RallyHttpClient(URI server, String userName, String password) {
//        this(new BasicAuthClient(server, userName, password));
//    }
//
//    public RallyHttpClient(URI server, String apiKey) {
//        this(new ApiKeyClient(server, apiKey));
//    }
//
//    protected RallyHttpClient(HttpClient httpClient) {
//        this.client = httpClient;
//    }
//
//    public void setProxy(URI proxy) {
//        this.client.setProxy(proxy);
//    }
//
//    public void setProxy(URI proxy, String userName, String password) {
//        this.client.setProxy(proxy, userName, password);
//    }
//
//    public void setApplicationVendor(String value) {
//        this.client.setApplicationVendor(value);
//    }
//
//    public void setApplicationVersion(String value) {
//        this.client.setApplicationVersion(value);
//    }
//
//    public void setApplicationName(String value) {
//        this.client.setApplicationName(value);
//    }
//
//    public String getWsapiVersion() {
//        return this.client.getWsapiVersion();
//    }
//
//    public void setWsapiVersion(String wsapiVersion) {
//        this.client.setWsapiVersion(wsapiVersion);
//    }
//
//    public CreateResponse create(CreateRequest request) throws IOException {
//        return new CreateResponse(this.client.doPost(request.toUrl(), request.getBody()));
//    }
//
//    public UpdateResponse update(UpdateRequest request) throws IOException {
//        return new UpdateResponse(this.client.doPost(request.toUrl(), request.getBody()));
//    }
//
//    public CollectionUpdateResponse updateCollection(CollectionUpdateRequest request) throws IOException {
//        return new CollectionUpdateResponse(this.client.doPost(request.toUrl(), request.getBody()));
//    }
//
//    public DeleteResponse delete(DeleteRequest request) throws IOException {
//        return new DeleteResponse(this.client.doDelete(request.toUrl()));
//    }
//
//    public QueryResponse query(QueryRequest request) throws IOException {
//        QueryResponse queryResponse = new QueryResponse(this.client.doGet(request.toUrl()));
//        if (queryResponse.wasSuccessful()) {
//            int receivedRecords = request.getPageSize();
//
//            while(receivedRecords < request.getLimit() && receivedRecords + request.getStart() - 1 < queryResponse.getTotalResultCount()) {
//                QueryRequest pageRequest = request.clone();
//                pageRequest.setStart(receivedRecords + request.getStart());
//                QueryResponse pageResponse = new QueryResponse(this.client.doGet(pageRequest.toUrl()));
//                if (pageResponse.wasSuccessful()) {
//                    JsonArray results = queryResponse.getResults();
//                    results.addAll(pageResponse.getResults());
//                    receivedRecords += pageRequest.getPageSize();
//                }
//            }
//        }
//
//        return queryResponse;
//    }
//
//    public GetResponse get(GetRequest request) throws IOException {
//        return new GetResponse(this.client.doGet(request.toUrl()));
//    }
//
//    public void close() throws IOException {
//        this.client.close();
//    }
//
//    public HttpClient getClient() {
//        return this.client;
//    }
//}