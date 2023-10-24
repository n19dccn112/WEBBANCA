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
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "CATEGORY_DESCRIPTION")
    private String categoryDescription;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_TYPE_ID")
    private CategoryType categoryType;

    @OneToMany(mappedBy = "category")
    private List<CategoryDetail> categoryDetails;
}
