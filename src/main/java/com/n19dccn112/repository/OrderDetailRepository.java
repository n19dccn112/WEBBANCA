package com.n19dccn112.repository;

import com.n19dccn112.model.entity.OrderDetail;
import com.n19dccn112.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByUnitDetail_UnitDetailId(Long productId);
    List<OrderDetail> findAllByOrder_OrderId(Long orderId);
    List<OrderDetail> findAllByOrder_OrderIdAndUnitDetail_UnitDetailId(Long orderId, Long unitDetailId);
}
