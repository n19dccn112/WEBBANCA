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
@Table(name = "FEATURE_TYPE")
public class FeatureType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEARTURE_TYPE_ID")
    private Long featureTypeId;

    @Column(name = "FEATURE_TYPE_NAME")
    private String featureTypeName;

    @Column(name = "FEATURE_TYPE_UNIT")
    private String featureTypeUnit;

    @Column(name = "IS_SHOW_FEATURE")
    private String isShow;

    @OneToMany(mappedBy = "featureType")
    private List<Feature> features;
}
