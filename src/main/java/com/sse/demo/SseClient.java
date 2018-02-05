package com.sse.demo;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class SseClient {
    final static Logger logger = Logger.getLogger(SseClient.class);

    public void sseRequest(final String url){
        EventInput eventInput = registerClient(url).request().get(EventInput.class);
        logger.info("Connection established with: " + url);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                // connection has been closed
                logger.info("No new server events, closing connection.");
                break;
            }
            logger.info(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
        }
    }

    public String sseRequest(final String url, final String interruptMessage){
        EventInput eventInput = registerClient(url).request().get(EventInput.class);
        logger.info("Connection established with: " + url);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent.readData(String.class).equals(interruptMessage)) {
                // interrupt our connection on target message
                logger.info("Breakpoint reached on message '" + interruptMessage + "'");
                return inboundEvent.readData(String.class);
            }
            logger.info(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
        }
        logger.info("No target breakpoint found while receiving sse.");
        return null;
    }

    public WebTarget registerClient(final String url){
        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        return client.target(url);
    }
}
