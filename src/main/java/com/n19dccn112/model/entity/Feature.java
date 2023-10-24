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
@Table(name = "FEATURE")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEATURE_ID")
    private Long featureId;

    @Column(name = "SPECIFIC")
    private String specific;

    @ManyToOne
    @JoinColumn(name = "FEARTURE_TYPE_ID")
    private FeatureType featureType;

    @OneToMany(mappedBy = "feature")
    private List<FeatureDetail> featureDetails;
}
