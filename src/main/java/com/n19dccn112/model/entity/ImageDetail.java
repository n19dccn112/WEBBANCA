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
@Table(name = "IMAGE_DETAIL")
public class ImageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_DETAIL_ID")
    private Long imageDetailId;

    @ManyToOne
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
