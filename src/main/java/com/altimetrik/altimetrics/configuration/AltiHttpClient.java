//package com.altimetrik.altimetrics.configuration;
//
//
//import java.io.Closeable;
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.Credentials;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DecompressingHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//
//public class AltiHttpClient extends DefaultHttpClient implements Closeable {
//
//    protected URI server;
//    protected String wsapiVersion = "v2.0";
//    protected HttpClient client;
//    private Map<AltiHttpClient.Header, String> headers = new HashMap<AltiHttpClient.Header, String>() {
//        {
//            this.put(AltiHttpClient.Header.Library, "Rally Rest API for Java v2.2.1");
//            this.put(AltiHttpClient.Header.Name, "Rally Rest API for Java");
//            this.put(AltiHttpClient.Header.Vendor, "Rally Software, Inc.");
//            this.put(AltiHttpClient.Header.Version, "2.2.1");
//        }
//    };
//
//    protected AltiHttpClient(URI server) {
//        this.server = server;
//        this.client = new HttpClient(this);
//    }
//
//    public void setProxy(URI proxy) {
//        this.getParams().setParameter("http.route.default-proxy", new HttpHost(proxy.getHost(), proxy.getPort(), proxy.getScheme()));
//    }
//
//    public void setProxy(URI proxy, String userName, String password) {
//        this.setProxy(proxy);
//        this.setClientCredentials(proxy, userName, password);
//    }
//
//    public void setApplicationVendor(String value) {
//        this.headers.put(AltiHttpClient.Header.Vendor, value);
//    }
//
//    public void setApplicationVersion(String value) {
//        this.headers.put(AltiHttpClient.Header.Version, value);
//    }
//
//    public void setApplicationName(String value) {
//        this.headers.put(AltiHttpClient.Header.Name, value);
//    }
//
//    public String getServer() {
//        return this.server.toString();
//    }
//
//    public String getWsapiVersion() {
//        return this.wsapiVersion;
//    }
//
//    public void setWsapiVersion(String wsapiVersion) {
//        this.wsapiVersion = wsapiVersion;
//    }
//
//    protected String doRequest(HttpRequestBase request) throws IOException {
//        Iterator var2 = this.headers.entrySet().iterator();
//
//        while(var2.hasNext()) {
//            Map.Entry<AltiHttpClient.Header, String> header = (Map.Entry)var2.next();
//            request.setHeader("X-RallyIntegration" + ((AltiHttpClient.Header)header.getKey()).name(), (String)header.getValue());
//        }
//
//        return this.executeRequest(request);
//    }
//
//    protected String executeRequest(HttpRequestBase request) throws IOException {
//        HttpResponse response = this.client.execute(request);
//        HttpEntity entity = response.getEntity();
//        if (response.getStatusLine().getStatusCode() == 200) {
//            return EntityUtils.toString(entity, "utf-8");
//        } else {
//            EntityUtils.consumeQuietly(entity);
//            throw new IOException(response.getStatusLine().toString());
//        }
//    }
//
//    public String doPost(String url, String body) throws IOException {
//        HttpPost httpPost = new HttpPost(this.getWsapiUrl() + url);
//        httpPost.setEntity(new StringEntity(body, "utf-8"));
//        return this.doRequest(httpPost);
//    }
//
//    public String doPut(String url, String body) throws IOException {
//        HttpPut httpPut = new HttpPut(this.getWsapiUrl() + url);
//        httpPut.setEntity(new StringEntity(body, "utf-8"));
//        return this.doRequest(httpPut);
//    }
//
//    public String doDelete(String url) throws IOException {
//        HttpDelete httpDelete = new HttpDelete(this.getWsapiUrl() + url);
//        return this.doRequest(httpDelete);
//    }
//
//    public String doGet(String url) throws IOException {
//        HttpGet httpGet = new HttpGet(this.getWsapiUrl() + url);
//        return this.doRequest(httpGet);
//    }
//
//    public void close()  {
//        this.client.getConnectionManager().shutdown();
//    }
//
//    protected Credentials setClientCredentials(URI server, String userName, String password) {
//        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
//        this.getCredentialsProvider().setCredentials(new AuthScope(server.getHost(), server.getPort()), credentials);
//        return credentials;
//    }
//
//    public String getWsapiUrl() {
//        return this.getServer() + "/slm/webservice/" + this.getWsapiVersion();
//    }
//
//    private static enum Header {
//        Library,
//        Name,
//        Vendor,
//        Version;
//
//        private Header() {
//        }
//    }
//}
