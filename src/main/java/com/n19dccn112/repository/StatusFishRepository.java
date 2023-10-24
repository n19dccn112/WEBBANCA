package com.n19dccn112.repository;

import com.n19dccn112.model.entity.StatusFish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusFishRepository extends JpaRepository<StatusFish, Long> {
}
