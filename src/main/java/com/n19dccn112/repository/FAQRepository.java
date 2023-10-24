package com.n19dccn112.repository;

import com.n19dccn112.model.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findAllByUserProduct_UserProductId(Long userProductId);
}
