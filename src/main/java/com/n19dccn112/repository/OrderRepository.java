package com.n19dccn112.repository;

import com.n19dccn112.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser_UserId(Long userId);
    List<Order> findAllByPaymentMethod_PaymentMethodId(Long paymentMethodId);
    List<Order> findAllByOrderStatus_OrderStatusId(Long orderStatusId);
    List<Order> findAllByUser_UserIdAndPaymentMethod_PaymentMethodId(Long userId, Long paymentMethodId);
    List<Order> findAllByUser_UserIdAndOrderStatus_OrderStatusId(Long userId, Long orderStatusId);
    List<Order> findAllByPaymentMethod_PaymentMethodIdAndOrderStatus_OrderStatusId(Long paymentMethodId, Long orderStatusId);
    List<Order> findAllByUser_UserIdAndPaymentMethod_PaymentMethodIdAndOrderStatus_OrderStatusId(Long userId, Long paymentMethodId, Long orderStatusId);

    @Query(value = "SELECT ORDER_ID FROM ORDERS WHERE ORDER_ID = " +
            "(SELECT MAX(ORDER_ID) FROM ORDERS where ORDER_PHONE like ?1);", nativeQuery = true)
    Long orderIdNewSave(String phone);

    @Query(value = "select * from ORDERS where PAYMENT_DATE IS NOT NULL and PAYMENT_DATE <= ?1 AND PAYMENT_DATE >= ?2", nativeQuery = true)
    List<Order> bCDT(Date dateFrom, Date dateTo);
}
