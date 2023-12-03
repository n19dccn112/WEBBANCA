package com.n19dccn112.service;

import com.n19dccn112.model.Auth.MessageResponse;
import com.n19dccn112.model.dto.OrderDTO;
import com.n19dccn112.model.entity.*;
import com.n19dccn112.repository.*;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class OrderService implements IBaseService<OrderDTO, Long>, IModelMapper<OrderDTO, Order> {
    private final OrderRepository orderRepository;
    private boolean cancelUpdate = false;
    private final UserRepository userRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ModelMapper modelMapper;
    private final PondRepository pondRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final StatusFishRepository statusFishRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderStatusRepository orderStatusRepository, PaymentMethodRepository paymentMethodRepository, ModelMapper modelMapper, PondRepository pondRepository, OrderDetailRepository orderDetailRepository, StatusFishDetailRepository statusFishDetailRepository, StatusFishRepository statusFishRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.modelMapper = modelMapper;
        this.pondRepository = pondRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.statusFishRepository = statusFishRepository;
    }

    @Override
    public List<OrderDTO> findAll() {
        return createFromEntities(orderRepository.findAll());
    }

    public List<OrderDTO> findAllByUserId(Long userId) {
        return createFromEntities(orderRepository.findAllByUser_UserId(userId));
    }
    public List<OrderDTO> findAllByPaymentMethodId(Long paymentMethodId) {
        return createFromEntities(orderRepository.findAllByPaymentMethod_PaymentMethodId(paymentMethodId));
    }
    public List<OrderDTO> findAllByOrderStatusId(Long orderStatusId) {
        return createFromEntities(orderRepository.findAllByOrderStatus_OrderStatusId(orderStatusId));
    }

    public List<OrderDTO> findAllByUserIdAndPaymentMethodId(Long userId, Long paymentMethodId){
        return createFromEntities(orderRepository.findAllByUser_UserIdAndOrderStatus_OrderStatusId(userId, paymentMethodId));
    }
    public List<OrderDTO> findAllByUserIdAndOrderStatusId(Long userId, Long orderStatusId){
        return createFromEntities(orderRepository.findAllByUser_UserIdAndOrderStatus_OrderStatusId(userId, orderStatusId));
    }
    public List<OrderDTO> findAllByPaymentMethodIdAndOrderStatusId(Long paymentMethodId, Long orderStatusId){
        return createFromEntities(orderRepository.findAllByPaymentMethod_PaymentMethodIdAndOrderStatus_OrderStatusId(paymentMethodId, orderStatusId));
    }

    public List<OrderDTO> findAllByUserIdAndPaymentMethodIdAndOrderStatusId(Long userId, Long paymentMethodId, Long orderStatusId){
        return createFromEntities(orderRepository.findAllByUser_UserIdAndPaymentMethod_PaymentMethodIdAndOrderStatus_OrderStatusId(userId, paymentMethodId, orderStatusId));
    }

    public List<OrderDTO> bCDT(Date dateFrom, Date dateTo){
        return createFromEntities(orderRepository.bCDT(dateFrom, dateTo));
    }

    @Override
    public OrderDTO findById(Long orderId) {
        return createFromE(orderRepository.findById(orderId).get());
    }

    @Override
    public OrderDTO update(Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId).get();
        OrderDTO orderDTO1 = createFromE(order);
        order = updateEntity(order, orderDTO);
        if (cancelUpdate){
            cancelUpdate = false;
            return orderDTO1;
        }
        orderRepository.save(order);
        return orderDTO;
    }

    public OrderDTO findByOrderIdNewSave(String orderPhone){
        return createFromE(orderRepository.findById(orderRepository.orderIdNewSave(orderPhone)).get());
    }

    public ResponseEntity<?> shipSuccess(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        try {
            if (order.getOrderStatus().getOrderStatusId() == 3L) {
                order.setOrderStatus(orderStatusRepository.findById(4L).get());
                order.setPaymentDate(new Date());
                orderRepository.save(order);
                return ResponseEntity.ok(new MessageResponse("Giao hàng thành công!"));
            }
        }catch (Exception e) {
            order.setOrderStatus(orderStatusRepository.findById(5L).get());
            order.setPaymentDate(null);
        }
        orderRepository.save(order);
        return ResponseEntity.ok(new MessageResponse("Giao hàng thất bại!"));
    }

    public ResponseEntity<?> shipCancel(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order.getOrderStatus().getOrderStatusId() == 3L) {
            order.setOrderStatus(orderStatusRepository.findById(5L).get());
            order.setPaymentDate(null);
            orderRepository.save(order);
            return ResponseEntity.ok(new MessageResponse("Hủy đơn hàng thành công!"));
        }
        return ResponseEntity.ok(new MessageResponse("Hủy đơn hàng thất bại!"));
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        orderDTO.setOrderStatusId(1L);
        orderDTO.setOrderTimeStart(new Date());
        orderDTO.setOrderStatusId(1L);
        if (orderDTO.getPaymentMethodId() == 1L){
            orderDTO.setPaymentDate(new Date());
        }
        orderRepository.save(createFromD(orderDTO));
        return orderDTO;
    }

    @Override
    public OrderDTO delete(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        try {
            orderRepository.delete(order);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Order.class, orderId);
        }
        return createFromE(order);
    }

    @Override
    public Order createFromD(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setUser(userRepository.findById(orderDTO.getUserId()).get());
        order.setOrderStatus(orderStatusRepository.findById(orderDTO.getOrderStatusId()).get());
        order.setPaymentMethod(paymentMethodRepository.findById(orderDTO.getPaymentMethodId()).get());
        order.setPaymentDate(orderDTO.getPaymentDate());
        return order;
    }

    @Override
    public OrderDTO createFromE(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        orderDTO.setUserId(order.getUser().getUserId());
        orderDTO.setOrderStatusId(order.getOrderStatus().getOrderStatusId());
        try {
            orderDTO.setPaymentMethodId(order.getPaymentMethod().getPaymentMethodId());
        }catch (Exception e){}
        return orderDTO;
    }

    @Override
    public Order updateEntity(Order order, OrderDTO orderDTO) {
        if (order != null && orderDTO != null){
            if (orderDTO.getOrderStatusId() != null) {

                Long statusBegin = orderDTO.getOrderStatusId();
                List<StatusFishDetail> statusFishDetailsNew = new ArrayList<>();
                List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_OrderId(order.getOrderId());

                if (orderDTO.getOrderStatusId() == 2L){
                    for (OrderDetail orderDetail: orderDetails) {
                        StatusFishDetail statusFishDetail = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(orderDetail.getUnitDetail().getUnitDetailId(), 1L).get(0);
                        int amount = orderDetail.getAmount();
                        if (statusFishDetail.getAmount() - amount > 0 && amount > 0){
                            statusFishDetail.setAmount(statusFishDetail.getAmount() - amount);
                            if (statusFishDetail.getAmount() < 0)  return order;
                            statusFishDetailsNew.add(statusFishDetail);
                        }
                    }
                    statusFishDetailRepository.saveAll(statusFishDetailsNew);
                }
                else if (orderDTO.getOrderStatusId() == 5L && statusBegin != 1L){
                    for (OrderDetail orderDetail: orderDetails) {
                        StatusFishDetail statusFishDetailBenh =  null;
                        StatusFishDetail statusFishDetailChet =  null;
                        int amountBenh = 0;
                        int amountChet = 0;
                        try {
                            statusFishDetailBenh = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(orderDetail.getUnitDetail().getUnitDetailId(), 2L).get(0);
                            amountBenh = orderDTO.getReAmounts().get(orderDetail.getOrderDetailId());
                            statusFishDetailBenh.setAmount(amountBenh);
                            statusFishDetailsNew.add(statusFishDetailBenh);
                        }catch (Exception e){}
                        try {
                            statusFishDetailChet = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(orderDetail.getUnitDetail().getUnitDetailId(), 3L).get(0);
                            amountChet = orderDetail.getAmount() - amountBenh;
                            statusFishDetailChet.setAmount(amountChet);
                            statusFishDetailsNew.add(statusFishDetailChet);
                            statusFishDetailRepository.saveAll(statusFishDetailsNew);
                        }catch (Exception e){}
                    }
                }
                order.setOrderStatus(orderStatusRepository.findById(statusBegin).get());
            }
            if (orderDTO.getOrderId() != null) {
                order.setUser(userRepository.findById(orderDTO.getOrderId()).get());
            }
            if (orderDTO.getPaymentMethodId() != null) {
                order.setPaymentMethod(paymentMethodRepository.findById(orderDTO.getPaymentMethodId()).get());
            }
            if (orderDTO.getOrderAddress() != null) {
                order.setOrderAddress(orderDTO.getOrderAddress());
            }
            if (orderDTO.getOrderPhone() != null) {
                order.setOrderPhone(orderDTO.getOrderPhone());
            }
            if (orderDTO.getOrderTimeStart() != null) {
                order.setOrderTimeStart(orderDTO.getOrderTimeStart());
            }
            if (orderDTO.getOrderTimeEnd() != null) {
                order.setOrderTimeEnd(orderDTO.getOrderTimeEnd());
            }
            if (orderDTO.getPaymentAmount() != null) {
                order.setPaymentAmount(orderDTO.getPaymentAmount());
            }
            if (orderDTO.getPaymentDate() != null) {
                order.setPaymentDate(orderDTO.getPaymentDate());
            }
        }
        return order;
    }
}
