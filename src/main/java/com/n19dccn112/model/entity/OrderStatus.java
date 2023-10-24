package com.n19dccn112.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDER_STATUS")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_STATUS_ID")
    private Long orderStatusId;

    @Column(name = "ORDER_STATUS_NAME")
    private String orderStatusName;

    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;
}
