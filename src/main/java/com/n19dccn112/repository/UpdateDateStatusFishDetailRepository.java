package com.n19dccn112.repository;

import com.n19dccn112.model.entity.StatusFishDetail;
import com.n19dccn112.model.entity.UpdateDateStatusFishDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UpdateDateStatusFishDetailRepository extends JpaRepository<UpdateDateStatusFishDetail, Long> {
    @Query(value = "select * from UPDATE_DATE_STATUS_FISH_DETAIL where UPDATE_DATE_STATUS_FISH_DETAIL_ID = ?1 and" +
            " UPDATE_DATE_STATUS_FISH_DETAIL > ?2", nativeQuery = true)
    List<UpdateDateStatusFishDetail> findAll(Long id, Date updateDate);
}
