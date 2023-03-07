package com.server.listener;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "socket")
public class SocketConfiguration {
    private String host;
    private int port;
}
