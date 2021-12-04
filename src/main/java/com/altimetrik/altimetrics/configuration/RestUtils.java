//package com.altimetrik.altimetrics.configuration;
//
//import com.rallydev.rest.client.HttpClient;
//import org.springframework.http.HttpHeaders;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class RestUtils {
//
//    private enum Header {
//        Library,
//        Name,
//        Vendor,
//        Version
//    }
//    private Map<RestUtils.Header, String> staticHeaders = new HashMap<>() {
//        {
//            put(RestUtils.Header.Library, "Rally Rest API for Java v2.2.1");
//            put(RestUtils.Header.Name, "Rally Rest API for Java");
//            put(RestUtils.Header.Vendor, "Rally Software, Inc.");
//            put(RestUtils.Header.Version, "2.2.1");
//        }
//    };
//    public void buildRequest(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("zsessionid", "_qWXWE5zFSnaScdgrPS101sLL244hokaAm37YNDiRtq8");
//        for (Map.Entry<Header, String> header : staticHeaders.entrySet()) {
//            headers.set("X-RallyIntegration" + header.getKey().name(), header.getValue());
//        }
//
//    }
//
//    public String getServer() {
//        return server.toString();
//    }
//
//    public String getWsapiUrl() {
//        return getServer() + "/slm/webservice/" + getWsapiVersion();
//    }
//}
