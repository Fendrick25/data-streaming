package com.server.streamserver.socket;

import com.server.streamserver.config.SocketConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@Slf4j
public class SocketHandler {
    private ServerSocket serverSocket;

    private PrintWriter writer;
    private BufferedReader in;

    @Autowired
    private SocketConfiguration socketConfiguration;

    @Async
    public void openConnection() throws IOException {
        serverSocket = new ServerSocket(socketConfiguration.getPort());
        log.info("Socket server started at port: {}", serverSocket.getLocalPort());
        while (true) {
            Socket clientSocket = serverSocket.accept();
            log.info("Client connected: {}", clientSocket.getInetAddress().getHostAddress());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            SocketMessagePublisher.addClient(clientSocket);
            log.info(in.readLine());
        }

    }

    public void close() throws IOException {

        try{
            if (serverSocket != null) {
                serverSocket.close();
                SocketMessagePublisher.closeClientsConnection();
            }
        }catch (Exception e){
            log.info("Error closing connection: {}", e.getMessage());
        }

    }
}
