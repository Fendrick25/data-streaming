package com.server.streamserver.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.streamserver.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SocketMessagePublisher {
    private static List<Socket> clients = new ArrayList<>();
    private static ObjectMapper mapper = new ObjectMapper();

    public static void addClient(Socket socket){
        clients.add(socket);
    }

    public static void removeClient(Socket socket){
        clients.remove(socket);
    }

    public static void sendAll(Order order){

        clients.forEach(client -> {
            try{
                OutputStream out = client.getOutputStream();
                String jsonString = mapper.writeValueAsString(order) + "\n";
                out.write(jsonString.getBytes(StandardCharsets.UTF_8));
                out.flush();
            }catch (Exception e){
                try {
                    SocketMessagePublisher.removeClient(client);
                    client.close();
                } catch (Exception ex) {
                    log.info("Error while closing client socket: {}", e.getMessage());
                }
                log.info("Error while sending message: {}", e.getMessage());
            }
        });

    }

    public static int getActiveClients(){
        return clients.size();
    }

    public static void closeClientsConnection() {
        clients.forEach(client -> {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
