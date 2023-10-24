package com.n19dccn112.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "IMPORT_DATE")
    private Date importDate;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "UPDATE_DATE_PRODUCT")
    private Date updateDateProduct;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "IS_ANIMAL")
    private String isAnimal;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<UnitDetail> unitDetails;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<BusinessDetail> businessDetails;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<CategoryDetail> categoryDetails;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<UserProduct> userProducts;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ImageDetail> imageDetails;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<EventProduct> eventProducts;
}
