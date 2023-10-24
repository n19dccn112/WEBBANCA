package com.n19dccn112.model.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BUSINESS_DETAIL")
public class BusinessDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUSINESS_DETAIL_ID")
    private Long businessDetailId;

    @Column(name = "BUSINESS_DATE_UPDATE")
    private Date businessDateUpdate;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID")
    private Business business;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
