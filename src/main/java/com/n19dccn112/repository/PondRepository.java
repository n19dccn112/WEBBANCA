package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Pond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PondRepository extends JpaRepository<Pond, Long> {
    List<Pond> findAllByUnitDetail_UnitDetailId(Long unitDetailId);
    @Query(value="SELECT * FROM POND WHERE UNIT_DETAIL_ID = ?1 " +
            "AND POND_AMOUNT = (SELECT MIN(POND_AMOUNT) FROM POND WHERE UNIT_DETAIL_ID = ?1)", nativeQuery = true)
    List<Pond> findFirstPond(Long unitDetailId);

    @Query(value="SELECT * FROM POND WHERE UNIT_DETAIL_ID = ?1 ", nativeQuery = true)
    List<Pond> findPond(Long unitDetailId);
}
