package com.n19dccn112.repository;
import com.n19dccn112.model.entity.Business;
import com.n19dccn112.model.entity.BusinessDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessDetailRepository extends JpaRepository<BusinessDetail, Long> {
    List<BusinessDetail> findAllByProduct_ProductId(Long productId);
    List<BusinessDetail> findAllByBusiness_BusinessId(Long businessId);
    List<BusinessDetail> findAllByBusiness_BusinessIdAndProduct_ProductId(Long businessId, Long productId);
}

