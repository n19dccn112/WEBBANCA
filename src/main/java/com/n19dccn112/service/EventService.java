package com.n19dccn112.service;

import com.n19dccn112.model.dto.EventDTO;
import com.n19dccn112.model.entity.Event;
import com.n19dccn112.repository.EventRepository;
import com.n19dccn112.repository.EventStatusRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.Constraint;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class EventService implements IBaseService<EventDTO, Long>, IModelMapper<EventDTO, Event> {
    private final EventRepository eventRepository;
    private final EventStatusRepository eventStatusRepository;
    private final ModelMapper modelMapper;

    public EventService(EventRepository eventRepository, EventStatusRepository eventStatusRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.eventStatusRepository = eventStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventDTO> findAll() {
        return createFromEntities(eventRepository.findAll());
    }

    public List<EventDTO> findAllEventStatusId(Long eventStatusId) {
        return createFromEntities(eventRepository.findAllByEventStatus_EventStatusId(eventStatusId));
    }

    @Override
    public EventDTO findById(Long eventId) {
        return createFromE(eventRepository.findById(eventId).get());
    }

    @Override
    public EventDTO update(Long eventId, EventDTO eventDTO) {
        Event event = eventRepository.findById(eventId).get();
        eventRepository.save(updateEntity(event, eventDTO));
        return eventDTO;
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        eventRepository.save(createFromD(eventDTO));
        return eventDTO;
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
        event.setEventStatus(eventStatusRepository.findById(eventDTO.getEventId()).get());
        return event;
    }

    @Override
    public EventDTO createFromE(Event event) {
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        eventDTO.setEventStatusId(event.getEventStatus().getEventStatusId());
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
            event.setEventStatus(eventStatusRepository.findById(eventDTO.getEventId()).get());
            event.setDiscountValue(eventDTO.getDiscountValue());
            event.setIsShow(eventDTO.getIsShow());
        }
        return null;
    }
}
