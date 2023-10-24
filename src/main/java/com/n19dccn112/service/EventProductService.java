package com.n19dccn112.service;

import com.n19dccn112.model.dto.EventProductDTO;
import com.n19dccn112.model.entity.EventProduct;
import com.n19dccn112.repository.EventProductRepository;
import com.n19dccn112.repository.EventRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class EventProductService implements IBaseService<EventProductDTO, Long>, IModelMapper<EventProductDTO, EventProduct> {
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;
    private final EventProductRepository eventProductRepository;
    private final ModelMapper modelMapper;

    public EventProductService(EventRepository eventRepository, ProductRepository productRepository, EventProductRepository eventProductRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
        this.eventProductRepository = eventProductRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventProductDTO> findAll() {
        return createFromEntities(eventProductRepository.findAll());
    }

    public List<EventProductDTO> findAllProductId(Long productId) {
        return createFromEntities(eventProductRepository.findAllByProduct_ProductId(productId));
    }

    public List<EventProductDTO> findAllEventId(Long eventId) {
        return createFromEntities(eventProductRepository.findAllByEvent_EventId(eventId));
    }

    public List<EventProductDTO> findAllEventIdAndProductId(Long eventId, Long productId) {
        return createFromEntities(eventProductRepository.findAllByEvent_EventIdAndProduct_ProductId(eventId, productId));
    }

    @Override
    public EventProductDTO findById(Long eventProductId) {
        return createFromE(eventProductRepository.findById(eventProductId).get());
    }

    @Override
    public EventProductDTO update(Long eventProductId, EventProductDTO eventProductDTO) {
        EventProduct eventProduct = eventProductRepository.findById(eventProductId).get();
        eventProductRepository.save(updateEntity(eventProduct, eventProductDTO));
        return eventProductDTO;
    }

    @Override
    public EventProductDTO save(EventProductDTO eventProductDTO) {
        eventProductRepository.save(createFromD(eventProductDTO));
        return eventProductDTO;
    }

    @Override
    public EventProductDTO delete(Long eventProductId) {
        EventProduct eventProduct = eventProductRepository.findById(eventProductId).get();
        try {
            eventProductRepository.delete(eventProduct);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(EventProduct.class, eventProductId);
        }
        return createFromE(eventProduct);
    }

    @Override
    public EventProduct createFromD(EventProductDTO eventProductDTO) {
        EventProduct eventProduct = modelMapper.map(eventProductDTO, EventProduct.class);
        eventProduct.setProduct(productRepository.findById(eventProductDTO.getProductId()).get());
        eventProduct.setEvent(eventRepository.findById(eventProductDTO.getEventId()).get());
        return eventProduct;
    }

    @Override
    public EventProductDTO createFromE(EventProduct eventProduct) {
        EventProductDTO eventProductDTO = modelMapper.map(eventProduct, EventProductDTO.class);
        eventProductDTO.setEventId(eventProductDTO.getEventId());
        eventProductDTO.setProductId(eventProductDTO.getProductId());
        return eventProductDTO;
    }

    @Override
    public EventProduct updateEntity(EventProduct eventProduct, EventProductDTO eventProductDTO) {
        if (eventProduct != null && eventProductDTO != null){
            eventProduct.setProduct(productRepository.findById(eventProductDTO.getProductId()).get());
            eventProduct.setEvent(eventRepository.findById(eventProductDTO.getEventId()).get());
            eventProduct.setUpdateDate(eventProductDTO.getUpdateDate());
            eventProduct.setCreateDate(eventProductDTO.getCreateDate());
        }
        return eventProduct;
    }
}
