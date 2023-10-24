package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Provinces;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, Integer> {
}
