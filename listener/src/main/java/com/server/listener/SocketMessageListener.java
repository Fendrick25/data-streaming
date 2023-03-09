package com.server.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Socket;

@Service
@Slf4j
public class SocketMessageListener {

    private BufferedReader in;
    private PrintWriter writer;
    private OutputStream out;
    private Socket socket;
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private SocketConfiguration socketConfiguration;

    @PostConstruct
    public void connectToServer() throws IOException{
        socket = new Socket(socketConfiguration.getHost(), socketConfiguration.getPort());
        log.info("Socket: {} connected", socketConfiguration.getHost());
        in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = socket.getOutputStream();
        log.info(in.readLine());
        String jsonString;
        while ((jsonString = in.readLine()) != null) {
            Order order = mapper.readValue(jsonString, Order.class);
            log.info("{}", order.getOrderId());
            writer = new PrintWriter(out);
            writer.println("From client: received order with id " + order.getOrderId());
            writer.flush();
        }
        log.info("Finished listening for data from server.");
    }

}
