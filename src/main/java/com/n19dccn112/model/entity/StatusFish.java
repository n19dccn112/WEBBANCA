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
@Table(name = "STATUS_FISH")
public class StatusFish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_FISH_ID")
    private Long statusFishId;

    @Column(name = "STATUS_FISH_NAME")
    private String statusFishName;

    @OneToMany(mappedBy = "statusFish")
    private List<StatusFishDetail> statusFishDetails;
}
