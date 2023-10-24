package com.n19dccn112.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "ORDER_ADDRESS")
    private String orderAddress;

    @Column(name = "ORDER_PHONE")
    private String orderPhone;

    @Column(name = "ORDER_TIME_START")
    private Date orderTimeStart;

    @Column(name = "ORDER_TIME_END")
    private Date orderTimeEnd;

    @Column(name = "PAYMENT_AMOUNT")
    private Integer paymentAmount;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_METHOD_ID")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "ORDER_STATUS_ID")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}
