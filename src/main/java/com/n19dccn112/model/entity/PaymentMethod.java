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
@Table(name = "PAYMENT_METHOD")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_METHOD_ID")
    private Long paymentMethodId;

    @Column(name = "PAYMENT_METHOD_NAME")
    private String paymentMethodName;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Order> orders;
}
