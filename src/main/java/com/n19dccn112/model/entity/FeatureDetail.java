package com.n19dccn112.model.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FEATURE_DETAIL")
public class FeatureDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEATURE_DETAIL_ID")
    private Long featureDetailId;

    @Column(name = "FEATURE_PRICE")
    private Integer featurePrice;

    @Column(name = "IMPORTANCE")
    private Integer importance;

    @ManyToOne
    @JoinColumn(name = "FEATURE_ID")
    private Feature feature;

    @ManyToOne
    @JoinColumn(name = "UNIT_DETAIL_ID")
    private UnitDetail unitDetail;
}
