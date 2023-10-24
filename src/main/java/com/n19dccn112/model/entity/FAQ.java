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
@Table(name = "FAQ")
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAQ_ID")
    private Long faqId;

    @Column(name = "QUESTION")
    private String question;

    @ManyToOne
    @JoinColumn(name = "USER_PRODUCT_ID")
    private UserProduct userProduct;

    @OneToMany(mappedBy = "faq")
    private List<Reply> replies;
}
