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
@Table(name = "CATEGORY_TYPE")
public class CategoryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_TYPE_ID")
    private Long categoryTypeId;

    @Column(name = "CATEGORY_TYPE_NAME")
    private String categoryTypeName;

    @Column(name = "CATEGORY_TYPE_DESCRIPTION")
    private String categoryTypeDescription;

    @OneToMany(mappedBy = "categoryType")
    private List<Category> categories;
}
