package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Category;
import com.n19dccn112.model.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findAllByFeatureType_FeatureTypeId(Long featureTypeId);
}
