package com.n19dccn112.repository;

import com.n19dccn112.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findAllByProvinces_ProvincesId(Integer provincesId);
}

