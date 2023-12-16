package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findAllByFeatureType_FeatureTypeId(Long featureTypeId);

    @Query(value = "select * from FEATURE where FEATURE_ID IN (SELECT FEATURE_ID FROM FEATURE_DETAIL WHERE UNIT_DETAIL_ID IN " +
            "(SELECT UNIT_DETAIL_ID FROM UNIT_DETAIL WHERE PRODUCT_ID = ?1))", nativeQuery = true)
    List<Feature> findAllByProductId(Long productId);
}
