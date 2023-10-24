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
@Table(name = "STATUS_FISH_DETAIL")
public class StatusFishDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_FISH_DETAIL_ID")
    private Long statusFishDetailId;

    @ManyToOne
    @JoinColumn(name = "UNIT_DETAIL_ID")
    private UnitDetail unitDetail;

    @ManyToOne
    @JoinColumn(name = "STATUS_FISH_ID")
    private StatusFish statusFish;
}