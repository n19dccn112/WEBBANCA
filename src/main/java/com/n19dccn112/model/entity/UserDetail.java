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
@Table(name = "USERS_DETAIL")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_DETAIL_ID")
    private Long userDetailId;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private District district;

    @ManyToOne
    @JoinColumn(name = "WARD_ID")
    private Ward ward;

    @ManyToOne
    @JoinColumn(name = "PROVINCE_ID")
    private Provinces provinces;
}
