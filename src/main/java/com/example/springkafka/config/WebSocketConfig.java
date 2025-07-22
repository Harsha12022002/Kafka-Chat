package com.example.springkafka.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.example.springkafka.SimpleTextHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SimpleTextHandler simpleTextHandler;

    public WebSocketConfig(SimpleTextHandler simpleTextHandler) {
        this.simpleTextHandler = simpleTextHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("WebSocket handler registered at /ws");

        registry.addHandler(simpleTextHandler, "/ws").setAllowedOrigins("*");
    }
}
