package com.server.streamserver;

import com.server.streamserver.socket.SocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class StreamServerApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context =  SpringApplication.run(StreamServerApplication.class, args);
		SocketHandler socketHandler = context.getBean(SocketHandler.class);
		socketHandler.openConnection();
	}

}
