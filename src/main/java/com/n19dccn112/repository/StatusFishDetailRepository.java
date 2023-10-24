package com.n19dccn112.repository;

import com.n19dccn112.model.entity.StatusFishDetail;
import com.n19dccn112.model.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusFishDetailRepository extends JpaRepository<StatusFishDetail, Long> {
    void deleteAllByUnitDetail_UnitDetailId(Long unitDetailId);
    List<StatusFishDetail> findAllByStatusFish_StatusFishId(Long statusFishId);
    List<StatusFishDetail> findAllByUnitDetail_UnitDetailId(Long unitDetailId);

    List<StatusFishDetail> findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(Long unitDetailId, Long statusFishId);
}
