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
@Table(name = "POND")
public class Pond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POND_ID")
    private Long pondId;

    @Column(name = "POND_NAME")
    private String pondName;

    @Column(name = "POND_AREA")
    private Integer pondArea;

    @Column(name = "STANDARD_PRICE")
    private Integer standardPrice;

    @Column(name = "POND_AMOUNT")
    private Integer pondAmount;

    @ManyToOne
    @JoinColumn(name = "UNIT_DETAIL_ID")
    private UnitDetail unitDetail;
}
