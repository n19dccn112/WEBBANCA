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
            "(SELECT MAX(STATUS_FISH_DETAIL_ID) FROM STATUS_FISH_DETAIL where UNIT_DETAIL_ID = ?1 AND STATUS_FISH_ID = ?2);", nativeQuery = true)
    Long statusFishDetailIdNewSave(Long unitDetail, Long statusId);

    @Query(value = "SELECT SUM(AMOUNT) " +
            "FROM UPDATE_DATE_STATUS_FISH_DETAIL a JOIN STATUS_FISH_DETAIL b on a.STATUS_FISH_DETAIL_ID = b.STATUS_FISH_DETAIL_ID " +
            "WHERE UPDATE_DATE_STATUS_FISH_DETAIL >= " +
            "(SELECT MAX(INPUT_DATE) FROM POND WHERE UNIT_DETAIL_ID = b.UNIT_DETAIL_ID AND UNIT_DETAIL_ID = ?1 GROUP BY UNIT_DETAIL_ID) " +
            "and b.STATUS_FISH_ID = ?2", nativeQuery = true)
    int findAllByUnitDetail_UnitDetailIdAndIdChetOrSong(Long unitDetailId, Long statusFishId);

    List<StatusFishDetail> findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(Long unitDetailId, Long statusFishId);
}
