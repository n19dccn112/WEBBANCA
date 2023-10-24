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
@Table(name = "provinces")
public class Provinces {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROVINCE_ID")
    private Integer provincesId;

    @Column(name = "PROVINCE_NAME")
    private String provincesName;

    @OneToMany(mappedBy = "provinces")
    private List<District> districts;

    @OneToMany(mappedBy = "provinces")
    private List<UserDetail> userDetails;
}
