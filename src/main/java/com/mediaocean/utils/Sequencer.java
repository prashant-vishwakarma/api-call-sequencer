package com.mediaocean.utils;

import com.google.common.util.concurrent.RateLimiter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.time.LocalTime;

public class Sequencer {

    private static final Double PERMITS_PER_SECONDS = 1d;
    private static final int PERMITS_CONSUMED = 1;

    private static RateLimiter rateLimiter;

    static {
        rateLimiter = RateLimiter.create(PERMITS_PER_SECONDS);
    }

    public static void dryRun(int index) {
        rateLimiter.acquire(PERMITS_CONSUMED);
        System.out.println("Running Index :: " + index + " :: " + LocalTime.now());
    }

    public static HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        rateLimiter.acquire(PERMITS_CONSUMED);
        HttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(httpUriRequest);
    }

}
