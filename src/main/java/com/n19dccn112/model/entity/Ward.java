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
@Table(name = "wards")
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WARD_ID")
    private Integer wardsId;

    @Column(name = "WARD_NAME")
    private String wardsName;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private District district;

    @OneToMany
    @JoinColumn(name = "ward")
    private List<UserDetail> userDetails;
}
