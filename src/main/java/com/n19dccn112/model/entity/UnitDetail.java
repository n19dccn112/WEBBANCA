package com.n19dccn112.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UNIT_DETAIL")
public class UnitDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_DETAIL_ID")
    private Long unitDetailId;

    @Column(name = "LENGHT")
    private float length;

    @Column(name = "SPEED_GROWTH")
    private float speedGrowth;

    @Column(name = "PRODUCT_PRICE")
    private Integer productPrice;

    @Column(name = "UNIT_UNIT_PRICE")
    private String unitUnitPrice;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToMany(mappedBy = "unitDetail")
    private List<FeatureDetail> featureDetails;

    @OneToMany(mappedBy = "unitDetail")
    private List<Pond> ponds;

    @OneToMany(mappedBy = "unitDetail")
    private List<StatusFishDetail> statusFishDetails;

    @OneToMany
    @JoinColumn(name = "unitDetail")
    private List<OrderDetail> orderDetails;
}
