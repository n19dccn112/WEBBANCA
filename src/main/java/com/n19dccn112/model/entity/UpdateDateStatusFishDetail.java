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
@Table(name = "UPDATE_DATE_STATUS_FISH_DETAIL")
public class UpdateDateStatusFishDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UPDATE_DATE_STATUS_FISH_DETAIL_ID")
    private Long updateDateStatusFishDetailId;

    @Column(name = "UPDATE_DATE_STATUS_FISH_DETAIL")
    private Date updateDate;

    @Column(name = "AMOUNT")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "STATUS_FISH_DETAIL_ID")
    private StatusFishDetail statusFishDetail;

    @ManyToOne
    @JoinColumn(name = "STATUS_FISH_ID_FROM")
    private StatusFish statusFishFrom;
}