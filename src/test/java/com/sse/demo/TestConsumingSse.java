package com.sse.demo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestConsumingSse {

    private static final String URL = "http://127.0.0.1:8081/sse";
    private static final String interruptMessage = "Possible break";

    @Test
    public void testSseExample(){
        SseClient client = new SseClient();
        client.sseRequest(URL);
    }

    @Test
    public void testSseExampleWithInterrup(){
        SseClient client = new SseClient();
        client.sseRequest(URL, interruptMessage);
    }
}
