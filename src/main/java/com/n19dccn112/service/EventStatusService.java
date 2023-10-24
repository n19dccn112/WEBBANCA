package com.n19dccn112.service;

import com.n19dccn112.model.dto.EventStatusDTO;
import com.n19dccn112.model.entity.EventStatus;
import com.n19dccn112.repository.EventStatusRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class EventStatusService implements IBaseService<EventStatusDTO, Long>, IModelMapper<EventStatusDTO, EventStatus> {
    private final EventStatusRepository eventStatusRepository;
    private final ModelMapper modelMapper;

    public EventStatusService(EventStatusRepository eventStatusRepository, ModelMapper modelMapper) {
        this.eventStatusRepository = eventStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventStatusDTO> findAll() {
        return createFromEntities(eventStatusRepository.findAll());
    }

    @Override
    public EventStatusDTO findById(Long eventStatusId) {
        return createFromE(eventStatusRepository.findById(eventStatusId).get());
    }

    @Override
    public EventStatusDTO update(Long eventStatusId, EventStatusDTO eventStatusDTO) {
        EventStatus eventStatus = eventStatusRepository.findById(eventStatusId).get();
        eventStatusRepository.save(updateEntity(eventStatus, eventStatusDTO));
        return eventStatusDTO;
    }

    @Override
    public EventStatusDTO save(EventStatusDTO eventStatusDTO) {
        eventStatusRepository.save(createFromD(eventStatusDTO));
        return eventStatusDTO;
    }

    @Override
    public EventStatusDTO delete(Long eventStatusId) {
        EventStatus eventStatus = eventStatusRepository.findById(eventStatusId).get();
        try {
            eventStatusRepository.delete(eventStatus);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(EventStatus.class, eventStatusId);
        }
        return createFromE(eventStatus);
    }

    @Override
    public EventStatus createFromD(EventStatusDTO eventStatusDTO) {
        EventStatus eventStatus = modelMapper.map(eventStatusDTO, EventStatus.class);
        return eventStatus;
    }

    @Override
    public EventStatusDTO createFromE(EventStatus eventStatus) {
        EventStatusDTO eventStatusDTO = modelMapper.map(eventStatus, EventStatusDTO.class);
        return eventStatusDTO;
    }

    @Override
    public EventStatus updateEntity(EventStatus eventStatus, EventStatusDTO eventStatusDTO) {
        if (eventStatus != null && eventStatusDTO != null){
            eventStatus.setEventStatusName(eventStatusDTO.getEventStatusName());
        }
        return eventStatus;
    }
}
