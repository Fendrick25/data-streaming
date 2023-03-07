package com.server.streamserver;

import com.server.streamserver.entity.OrderEntity;
import com.server.streamserver.repository.OrderJpaRepository;
import com.server.streamserver.socket.SocketMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderJpaRepository orderJpaRepository;

    @Scheduled(fixedRate = 5000)
    public void sendData()  {
        if(SocketMessagePublisher.getActiveClients() != 0){
            List<Order> orders = orderJpaRepository.findAll().stream()
                    .map(orderEntity -> Order.builder()
                            .orderId(orderEntity.getId())
                            .accountId(orderEntity.getAccountId())
                            .code(orderEntity.getCode())
                            .amount(orderEntity.getAmount())
                            .price(orderEntity.getPrice())
                            .build()).toList();

            for (Order order : orders) {
                try{
                    SocketMessagePublisher.sendAll(order);
                }catch (Exception e){
                    log.info("{}", e.getMessage());
                }

            }
        }
        log.info("{}", SocketMessagePublisher.getActiveClients());
    }
}
