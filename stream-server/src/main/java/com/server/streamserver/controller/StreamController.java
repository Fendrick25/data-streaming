package com.server.streamserver.controller;

import com.server.streamserver.socket.SocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/streams")
public class StreamController {
    private final SocketHandler socketPublisher;

    @PostMapping("/start")
    public ResponseEntity<Void> startStream(){
        try {
            socketPublisher.openConnection();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stopStream(){
        try {
            socketPublisher.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
