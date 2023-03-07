package com.server.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SocketMessageListener {

    private BufferedReader in;
    private Socket socket;
    private ObjectMapper mapper = new ObjectMapper();

    private List<Order> orders = new ArrayList<>();

    @Autowired
    private SocketConfiguration socketConfiguration;

    @PostConstruct
    public void listen() throws IOException {
        socket = new Socket(socketConfiguration.getHost(), socketConfiguration.getPort());
        log.info("Socket: {} connected", socketConfiguration.getHost());
        in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String jsonString;
        while ((jsonString = in.readLine()) != null) {
            Order order = mapper.readValue(jsonString, Order.class);
            orders.add(order);
            log.info("{}", order.getOrderId());
        }
        log.info("Finished listening for data from server.");
    }

    @Scheduled(fixedRate = 7000)
    public void showListSize(){
        System.out.println("hit");
        log.info("list side: {}", orders.size());
    }


}
