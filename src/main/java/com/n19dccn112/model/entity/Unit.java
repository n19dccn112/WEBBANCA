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
@Table(name = "UNIT")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID")
    private Long unitId;

    @Column(name = "UNIT_NAME")
    private String unitName;

    @Column(name = "UNIT_DESCRIPTION")
    private String unitDescription;

    @OneToMany(mappedBy = "unit")
    private List<UnitDetail> unitDetails;
}
