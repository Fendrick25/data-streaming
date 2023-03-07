package com.server.streamserver.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;
    private String accountId;
    private String code;
    private int amount;
    private Double price;
}
