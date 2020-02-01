package com.mediaocean.ui;

import com.mediaocean.tasks.CallableTask;

import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {

    private static Logger apacheLogger = java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies");

    public static void main(String[] args) {

        //        //To Suppress Irrelevant Warnings
        //        apacheLogger.setLevel(Level.OFF);
        //
        //        String getApiUri = "http://dummy.restapiexample.com/api/v1/employees";
        //        String postApiUri = "http://dummy.restapiexample.com/api/v1/create";
        //        String degreedAuthTokenApiUri = "https://degreed.com/oauth/token";
        //
        //        //String getResponse = getCall(getApiUri);
        //        //System.out.println("Get Response:\n" + getResponse);
        //        String postResponse = getAuthTokenPost(degreedAuthTokenApiUri);
        //        System.out.println("Post Response:\n" + postResponse);

        //Sequencer sequencer = new Sequencer();

        //ExecutorService executorService = new ThreadPoolExecutor(10, 100, 5L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ExecutorService executorService = Executors.newFixedThreadPool(70);

        List<Callable<Integer>> taskList = new ArrayList<>();

        for (int i = 0; i < 70; i++) {
            taskList.add(new CallableTask(i));
        }

        try {
            executorService.invokeAll(taskList);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdownNow();
    }

    static private String getAuthTokenPost(String tokenUri) {
        //String paramString = "someParameterString";

        //Client is Executed for REST Call
        HttpClient client = HttpClients.createDefault();

        try {
            //Build Appropriate URI using Apache Utils
            URIBuilder builder = new URIBuilder(tokenUri);
            //Request Params can be added if required.
            //builder.addParameter("", "");

            //Build URI After adding Parameters
            String builtUri = builder.build().toString();
            System.out.println(builtUri);
            //For GET Method: HttpGet
            HttpPost httpPost = new HttpPost(builtUri);
            //Add Headers to Post Request
            httpPost.addHeader(HttpHeaders.ACCEPT, "application/x-www-form-urlencoded");

            //String jsonBody = "{\"name\":\"Prashant\",\"salary\":\"123\",\"age\":\"22\"}";
            //StringEntity entity = new StringEntity(jsonBody);

            List<NameValuePair> bodyItems = new ArrayList<NameValuePair>();
            bodyItems.add(new BasicNameValuePair("grant_type", "client_credentials"));
            bodyItems.add(new BasicNameValuePair("client_id", "216a63abee704c1c"));
            bodyItems.add(new BasicNameValuePair("client_secret", "286d7435ef057fd30227fc3ef6f7470e"));
            bodyItems.add(new BasicNameValuePair("scope", "pathways:read,content:read,completions:read"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(bodyItems, Consts.UTF_8);

            //Add body to Post Request
            httpPost.setEntity(entity);

            //Execute client for REST Call
            HttpResponse httpResponse;
            httpResponse = client.execute(httpPost);

            //Check StatusCode for Success (200)
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                //Handle Status Code other than 2XX here
                System.out.println("Request Did Not Succeed");
                return "Status:" + statusCode;
            }

            //Get Response Body
            //Print/ Return Response
            return EntityUtils.toString(httpResponse.getEntity());

        }
        catch (URISyntaxException e) {
            //Trigger when URI Builder Fails
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            //Trigger when client Execute Fails
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "Exception Occured";
    }

    static private String getCall(String uri) {
        //String paramString = "someParameterString";

        //Client is Executed for REST Call
        HttpClient client = HttpClients.createDefault();

        try {
            //Build Appropriate URI using Apache Utils
            URIBuilder builder = new URIBuilder(uri);
            //Request Params can be added if required.
            //builder.addParameter('paramName', paramString);

            //Build URI After adding Parameters
            String builtUri = builder.build().toString();
            //For GET Method: HttpGet
            HttpGet httpGet = new HttpGet(builtUri);
            //Add Headers to GET Request
            //httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");

            //Execute client for REST Call
            HttpResponse httpResponse = client.execute(httpGet);

            //Check StatusCode for Success (200)
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                //Handle Status Code other than 2XX here
                System.out.println("Request Did Not Succeed");
                return "Status:" + statusCode;
            }

            //Get Response Body
            //Print/ Return Response
            return EntityUtils.toString(httpResponse.getEntity());

        }
        catch (URISyntaxException e) {
            //Trigger when URI Builder Fails
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            //Trigger when client Execute Fails
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "Exception Occured";
    }

    static private String postCall(String uri) {
        //String paramString = "someParameterString";

        //Client is Executed for REST Call
        HttpClient client = HttpClients.createDefault();

        try {
            //Build Appropriate URI using Apache Utils
            URIBuilder builder = new URIBuilder(uri);
            //Request Params can be added if required.
            builder.addParameter("scope", "pathways=read,content:read,completions:read");

            //Build URI After adding Parameters
            String builtUri = builder.build().toString();
            System.out.println(builtUri);
            //For GET Method: HttpGet
            HttpPost httpPost = new HttpPost(builtUri);
            //Add Headers to Post Request
            //httpPost.addHeader(HttpHeaders.ACCEPT, "application/json");

            String jsonBody = "{\"name\":\"Prashant\",\"salary\":\"123\",\"age\":\"22\"}";
            StringEntity entity = new StringEntity(jsonBody);

            //Add body to Post Request
            httpPost.setEntity(entity);

            //Execute client for REST Call
            HttpResponse httpResponse;
            httpResponse = client.execute(httpPost);

            //Check StatusCode for Success (200)
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                //Handle Status Code other than 2XX here
                System.out.println("Request Did Not Succeed");
                return "Status:" + statusCode;
            }

            //Get Response Body
            //Print/ Return Response
            return EntityUtils.toString(httpResponse.getEntity());

        }
        catch (URISyntaxException e) {
            //Trigger when URI Builder Fails
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            //Trigger when client Execute Fails
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return "Exception Occured";
    }

}
