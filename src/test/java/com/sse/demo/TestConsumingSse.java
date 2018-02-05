package com.sse.demo;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TestConsumingSse {

    final static Logger logger = Logger.getLogger(TestConsumingSse.class);

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
        Assert.assertEquals(interruptMessage, client.sseRequest(URL, interruptMessage));
    }
}
