package com.n19dccn112.repository;

import com.n19dccn112.model.entity.CategoryDetail;
import com.n19dccn112.model.entity.EventProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventProductRepository extends JpaRepository<EventProduct, Long> {
    List<EventProduct> findAllByProduct_ProductId(Long productId);
    List<EventProduct> findAllByEvent_EventId(Long eventId);
    List<EventProduct> findAllByEvent_EventIdAndProduct_ProductId(Long eventId, Long productId);
}
