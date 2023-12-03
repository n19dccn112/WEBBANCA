package com.n19dccn112.service;

import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.model.dto.ProductDTO;
import com.n19dccn112.model.entity.Event;
import com.n19dccn112.model.entity.EventProduct;
import com.n19dccn112.repository.EventProductRepository;
import com.n19dccn112.repository.EventRepository;
import com.n19dccn112.repository.EventStatusRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventService implements IBaseService<EventDTO, Long>, IModelMapper<EventDTO, Event> {
    private final EventRepository eventRepository;
    private final EventProductRepository eventProductRepository;
    private final ProductService productService;
    private final EventStatusRepository eventStatusRepository;
    private final ModelMapper modelMapper;

    public EventService(EventRepository eventRepository, EventProductRepository eventProductRepository, ProductService productService, EventStatusRepository eventStatusRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.eventProductRepository = eventProductRepository;
        this.productService = productService;
        this.eventStatusRepository = eventStatusRepository;
        this.modelMapper = modelMapper;
    }

    public void updateStatus(EventDTO eventDTO){
        if (compareDates(eventDTO.getStartDate(), eventDTO.getEndDate()) <= 0) {
            Date now = new Date();
            if (compareDates(eventDTO.getStartDate(), now) <= 0 && compareDates(eventDTO.getEndDate(), now) >= 0) {
                eventDTO.setEventStatusId(2L);
            } else if (compareDates(eventDTO.getStartDate(), now) > 0) {
                eventDTO.setEventStatusId(1L);
            } else {
                eventDTO.setEventStatusId(3L);
            }
            eventRepository.save(createFromD(eventDTO));
        }
    }

    @Override
    public List<EventDTO> findAll() {
        List<EventDTO> eventDTOS = createFromEntities(eventRepository.findAll());
        for (EventDTO eventDTO: eventDTOS){
            updateStatus(eventDTO);
            eventDTOS = createFromEntities(eventRepository.findAll());
        }
        return eventDTOS;
    }

    public List<EventDTO> findAllEventWillGoOrHaveGoing() {
        return createFromEntities(eventRepository.findAllEventWillGoOrHaveGoing());
    }

    public List<EventDTO> findAllEventStatusId(Long eventStatusId) {
        return createFromEntities(eventRepository.findAllByEventStatus_EventStatusId(eventStatusId));
    }

    @Override
    public EventDTO findById(Long eventId) {
        EventDTO eventDTO = createFromE(eventRepository.findById(eventId).get());
        updateStatus(eventDTO);
        eventDTO = createFromE(eventRepository.findById(eventId).get());
        return eventDTO;
    }
    @Transactional
    public void saveProductEvent(Event event, EventDTO eventDTO){
        for (ProductDTO productDTO: eventDTO.getProductDTOS()){
            EventProduct eventProduct = new EventProduct();
            eventProduct.setEvent(event);
            eventProduct.setCreateDate(new Date());
            eventProduct.setUpdateDate(new Date());
            eventProduct.setProduct(productService.createFromD(productDTO));
            eventProductRepository.save(eventProduct);
        }
    }

    @Transactional
    public void updateProductEvent(Event event, EventDTO eventDTO){
        List <EventProduct> eventProducts = eventProductRepository.findAllByEvent_EventId(event.getEventId());
        eventProductRepository.deleteAll(eventProducts);

        List <EventProduct> eventProducts2 = eventProductRepository.findAllByEvent_EventId(event.getEventId());
        saveProductEvent(event, eventDTO);
    }

    @Transactional
    @Override
    public EventDTO update(Long eventId, EventDTO eventDTO) {
        Event event = eventRepository.findById(eventId).get();
        eventRepository.save(updateEntity(event, eventDTO));
        updateProductEvent(event, eventDTO);
        updateStatus(createFromE(event));
        return eventDTO;
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        updateStatus(eventDTO);
        Event event = eventRepository.findById(eventRepository.eventIdNewSave(eventDTO.getEventName())).get();
        saveProductEvent(event, eventDTO);
        return eventDTO;
    }

    private static Calendar dates(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
    private static int compareDates(Date date1, Date date2) {
        Calendar cal1 = dates(date1);
        Calendar cal2 = dates(date2);
        return cal1.getTime().compareTo(cal2.getTime());
    }

    @Override
    public EventDTO delete(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        try {
            eventRepository.delete(event);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Event.class, eventId);
        }
        return createFromE(event);
    }

    @Override
    public Event createFromD(EventDTO eventDTO) {
        Event event= modelMapper.map(eventDTO, Event.class);
        event.setEventStatus(eventStatusRepository.findById(eventDTO.getEventStatusId()).get());
        return event;
    }

    @Override
    public EventDTO createFromE(Event event) {
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        try {
            eventDTO.setEventStatusId(event.getEventStatus().getEventStatusId());
        }catch (Exception e){}
        try {
            eventDTO.setProductDTOS(productService.findAllByEventId(event.getEventId()));
        }catch (Exception e){}
        return eventDTO;
    }

    @Override
    public Event updateEntity(Event event, EventDTO eventDTO) {
        if (event != null && eventDTO != null){
            event.setEndDate(eventDTO.getEndDate());
            event.setStartDate(eventDTO.getStartDate());
            event.setDiscountCode(eventDTO.getDiscountCode());
            event.setEventName(eventDTO.getEventName());
            event.setEventDescription(eventDTO.getEventDescription());
            event.setDiscountValue(eventDTO.getDiscountValue());
        }
        return event;
    }
}
