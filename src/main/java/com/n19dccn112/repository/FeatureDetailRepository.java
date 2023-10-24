package com.n19dccn112.repository;

import com.n19dccn112.model.entity.FeatureDetail;
import com.n19dccn112.model.entity.FeatureDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureDetailRepository extends JpaRepository<FeatureDetail, Long> {
    List<FeatureDetail> findAllByUnitDetail_UnitDetailId(Long unitDetailId);
    List<FeatureDetail> findAllByFeature_FeatureId(Long featureId);
    List<FeatureDetail> findAllByFeature_FeatureIdAndUnitDetail_UnitDetailId(Long unitDetailId, Long productId);

    void deleteAllByUnitDetail_UnitDetailId(Long unitDetailId);
}
