package com.n19dccn112.repository;

import com.n19dccn112.model.entity.UserProduct;
import com.n19dccn112.model.key.RateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<UserProduct, RateId> {
    @Query(value = "select * from user_product where product_id = ?1", nativeQuery = true)
    List<UserProduct> findAllByRateIdProduct_ProductId(Long productId);
}
