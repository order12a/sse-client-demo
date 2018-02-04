package com.sse.demo;

import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class SseClient {

    public void sseRequest(final String url){
        EventInput eventInput = registerClient(url).request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                // connection has been closed
                break;
            }
            System.out.println(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
        }
    }

    public String sseRequest(final String url, final String interruptMessage){
        EventInput eventInput = registerClient(url).request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent.readData(String.class).equals(interruptMessage)) {
                // interrupt our connection on target message
                return inboundEvent.readData(String.class);
            }
            System.out.println(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
        }
        return null;
    }

    public WebTarget registerClient(final String url){
        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        return client.target(url);
    }
}
