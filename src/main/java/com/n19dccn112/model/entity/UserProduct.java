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
@Table(name = "USER_PRODUCT")
public class UserProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_PRODUCT_ID")
    private Long userProductId;

    @Column(name = "COMMNET")
    private String comment;

    @Column(name = "POINT")
    private Integer point;

    @Column(name = "IS_LOVE")
    private String isLove;

    @Column(name = "IS_SEEN")
    private String isSeen;

    @Column(name = "ID_USER_PRODUCT_RELY")
    private Long idUserProductReply;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
