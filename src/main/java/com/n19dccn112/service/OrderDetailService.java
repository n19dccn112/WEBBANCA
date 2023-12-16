package com.n19dccn112.service;

import com.n19dccn112.model.dto.OrderDetailDTO;
import com.n19dccn112.model.entity.*;
import com.n19dccn112.repository.*;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IBaseService<OrderDetailDTO, Long>, IModelMapper<OrderDetailDTO, OrderDetail> {
    private final OrderRepository orderRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final PondRepository pondRepository;
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final StatusFishDetailService statusFishDetailService;

    public OrderDetailService(OrderRepository orderRepository, UnitDetailRepository unitDetailRepository, OrderDetailRepository orderDetailRepository, ModelMapper modelMapper, PondRepository pondRepository, StatusFishDetailRepository statusFishDetailRepository, StatusFishDetailService statusFishDetailService) {
        this.orderRepository = orderRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.modelMapper = modelMapper;
        this.pondRepository = pondRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.statusFishDetailService = statusFishDetailService;
    }

    @Override
    public List<OrderDetailDTO> findAll() {
        return createFromEntities(orderDetailRepository.findAll());
    }

    public List<OrderDetailDTO> findAllUnitDetail(Long unitDetailId) {
        return createFromEntities(orderDetailRepository.findAllByUnitDetail_UnitDetailId(unitDetailId));
    }

    public List<OrderDetailDTO> findAllOrderId(Long orderId) {
        return createFromEntities(orderDetailRepository.findAllByOrder_OrderId(orderId));
    }

    public List<OrderDetailDTO> findAlOrderIdAndUnitDetailId(Long orderId, Long unitDetailId) {
        return createFromEntities(orderDetailRepository.findAllByOrder_OrderIdAndUnitDetail_UnitDetailId(orderId, unitDetailId));
    }

    @Override
    public OrderDetailDTO findById(Long orderDetailId) {
        return createFromE(orderDetailRepository.findById(orderDetailId).get());
    }

    @Override
    public OrderDetailDTO update(Long orderDetailId, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).get();
        orderDetailRepository.save(updateEntity(orderDetail, orderDetailDTO));
        return orderDetailDTO;
    }
    @Override
    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        orderDetailRepository.save(createFromD(orderDetailDTO));
        return orderDetailDTO;
    }

    @Override
    public OrderDetailDTO delete(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).get();
        try {
            orderDetailRepository.save(orderDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(OrderDetail.class, orderDetailId);
        }
        return createFromE(orderDetail);
    }

    @Override
    public OrderDetail createFromD(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        orderDetail.setOrder(orderRepository.findById(orderDetailDTO.getOrderId()).get());
        orderDetail.setUnitDetail(unitDetailRepository.findById(orderDetailDTO.getUnitDetailId()).get());
        return orderDetail;
    }

    @Override
    public OrderDetailDTO createFromE(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
        orderDetailDTO.setOrderId(orderDetail.getOrder().getOrderId());
        orderDetailDTO.setUnitDetailId(orderDetail.getUnitDetail().getUnitDetailId());
        return orderDetailDTO;
    }

    @Override
    public OrderDetail updateEntity(OrderDetail orderDetail, OrderDetailDTO orderDetailDTO) {
        if (orderDetail != null && orderDetailDTO != null){
            orderDetail.setAmount(orderDetailDTO.getAmount());
        }
        return orderDetail;
    }
}
