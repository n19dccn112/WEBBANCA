package com.n19dccn112.service;

import com.n19dccn112.model.dto.OrderStatusDTO;
import com.n19dccn112.model.entity.OrderStatus;
import com.n19dccn112.repository.OrderStatusRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class OrderStatusService implements IBaseService<OrderStatusDTO, Long>, IModelMapper<OrderStatusDTO, OrderStatus> {
    private final OrderStatusRepository orderStatusRepository;
    private final ModelMapper modelMapper;

    public OrderStatusService(OrderStatusRepository orderStatusRepository, ModelMapper modelMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OrderStatusDTO> findAll() {
        return createFromEntities(orderStatusRepository.findAll());
    }

    @Override
    public OrderStatusDTO findById(Long orderStatusId) {
        return createFromE(orderStatusRepository.findById(orderStatusId).get());
    }

    @Override
    public OrderStatusDTO update(Long orderStatusId, OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId).get();
        orderStatusRepository.save(updateEntity(orderStatus, orderStatusDTO));
        return orderStatusDTO;
    }

    @Override
    public OrderStatusDTO save(OrderStatusDTO orderStatusDTO) {
        orderStatusRepository.save(createFromD(orderStatusDTO));
        return orderStatusDTO;
    }

    @Override
    public OrderStatusDTO delete(Long orderStatusId) {
        OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId).get();
        try {
            orderStatusRepository.delete(orderStatus);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(OrderStatus.class, orderStatusId);
        }
        return createFromE(orderStatus);
    }

    @Override
    public OrderStatus createFromD(OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = modelMapper.map(orderStatusDTO, OrderStatus.class);
        return orderStatus;
    }

    @Override
    public OrderStatusDTO createFromE(OrderStatus orderStatus) {
        OrderStatusDTO orderStatusDTO = modelMapper.map(orderStatus, OrderStatusDTO.class);
        return orderStatusDTO;
    }

    @Override
    public OrderStatus updateEntity(OrderStatus orderStatus, OrderStatusDTO orderStatusDTO) {
        if (orderStatus != null && orderStatusDTO != null){
            orderStatus.setOrderStatusName(orderStatusDTO.getOrderStatusName());
        }
        return orderStatus;
    }
}
