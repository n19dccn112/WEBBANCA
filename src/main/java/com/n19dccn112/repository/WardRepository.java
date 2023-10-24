package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Ward;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findAllByDistrict_DistrictsId(Integer districtId);
}
