package com.server.listener;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private UUID orderId;
    private String accountId;
    private String code;
    private int amount;
    private Double price;
}
