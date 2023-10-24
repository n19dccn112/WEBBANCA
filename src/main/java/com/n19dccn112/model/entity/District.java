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
@Table(name = "DISTRICTS")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISTRICT_ID")
    private Integer districtsId;

    @Column(name = "DISTRICT_NAME")
    private String districtsName;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_ID")
    private Provinces provinces;

    @OneToMany(mappedBy = "district")
    private List<Ward> wards;

    @OneToMany(mappedBy = "district")
    private List<UserDetail> userDetails;
}
