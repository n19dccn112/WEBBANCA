package com.n19dccn112.repository;

import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.model.entity.Unit;
import com.n19dccn112.model.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetail, Long> {
    List<UnitDetail> findAllByProduct_ProductId(Long productId);
    List<UnitDetail> findAllByUnit_UnitId(Long unitId);

    List<UnitDetail> findAllByUnit_UnitIdAndProduct_ProductId(Long unitId, Long productId);
    void deleteAllByProduct_ProductId(Long productId);
    @Query(value = "SELECT UNIT_DETAIL_ID FROM UNIT_DETAIL WHERE UNIT_DETAIL_ID = " +
            "(SELECT MAX(UNIT_DETAIL_ID) FROM UNIT_DETAIL where UNIT_ID = ?1 AND PRODUCT_ID = ?2);", nativeQuery = true)
    Long userDetailIdNewSave(Long unitId, Long productId);
}
