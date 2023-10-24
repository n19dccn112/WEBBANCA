package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.model.entity.Image;
import com.n19dccn112.model.entity.Unit;
import com.n19dccn112.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query(value = "SELECT U.* " +
            "FROM UNIT u " +
            "WHERE u.UNIT_ID IN (SELECT ud.UNIT_ID FROM UNIT_DETAIL ud WHERE ud.PRODUCT_ID = ?1)", nativeQuery = true)
    List<Unit> findAllByProductId(Long productId);
}
