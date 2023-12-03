package com.n19dccn112.repository;

import com.n19dccn112.model.entity.StatusFishDetail;
import com.n19dccn112.model.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatusFishDetailRepository extends JpaRepository<StatusFishDetail, Long> {
    void deleteAllByUnitDetail_UnitDetailId(Long unitDetailId);
    List<StatusFishDetail> findAllByStatusFish_StatusFishId(Long statusFishId);
    List<StatusFishDetail> findAllByUnitDetail_UnitDetailId(Long unitDetailId);
    @Query(value = "SELECT STATUS_FISH_DETAIL_ID FROM STATUS_FISH_DETAIL WHERE STATUS_FISH_DETAIL_ID = " +
            "(SELECT MAX(STATUS_FISH_DETAIL_ID) FROM STATUS_FISH_DETAIL where STATUS_FISH_DATE_UPDATE = ?1);", nativeQuery = true)
    Long statusFishDetailIdNewSave(Date updateDate);
    List<StatusFishDetail> findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(Long unitDetailId, Long statusFishId);
}
