package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.configuration.HibernateSearchConfig;
import com.hillel.evo.adviser.dto.WSInputDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AdviserStarter.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(value = {"/create-business.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WebSocketControllerTest {

    @Autowired
    private HibernateSearchConfig hibernateSearchConfig;

    private String WEBSOCKET_URI = "http://localhost:8080/api/socket";
    static final String WEBSOCKET_SUB = "/list/result";
    static final String WEBSOCKET_SEND = "/app/socket/message";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));

    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {

        hibernateSearchConfig.reindex();

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);
        session.subscribe(WEBSOCKET_SUB, new DefaultStompFrameHandler());

        WSInputDTO message = new WSInputDTO();
        message.setSearchType("BusinessType");
        message.setContent("sh");

        session.send(WEBSOCKET_SEND, message.toString().getBytes());

        assertTrue(blockingQueue.poll(1, TimeUnit.SECONDS).contains("result"));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
