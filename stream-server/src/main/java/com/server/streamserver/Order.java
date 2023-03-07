package com.server.streamserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private UUID orderId;
    private String accountId;
    private String code;
    private int amount;
    private Double price;
}
