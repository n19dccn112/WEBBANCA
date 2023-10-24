package com.n19dccn112.service;

import com.n19dccn112.model.dto.StatusFishDTO;
import com.n19dccn112.model.entity.StatusFish;
import com.n19dccn112.repository.StatusFishRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class StatusFishService implements IBaseService<StatusFishDTO, Long>, IModelMapper<StatusFishDTO, StatusFish> {
    private final StatusFishRepository statusFishRepository;
    private final ModelMapper modelMapper;

    public StatusFishService(StatusFishRepository statusFishRepository, ModelMapper modelMapper) {
        this.statusFishRepository = statusFishRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StatusFishDTO> findAll() {
        return createFromEntities(statusFishRepository.findAll());
    }

    @Override
    public StatusFishDTO findById(Long statusFishId) {
        return createFromE(statusFishRepository.findById(statusFishId).get());
    }

    @Override
    public StatusFishDTO update(Long statusFishId, StatusFishDTO statusFishDTO) {
        StatusFish statusFish = statusFishRepository.findById(statusFishId).get();
        statusFishRepository.save(createFromD(statusFishDTO));
        return statusFishDTO;
    }

    @Override
    public StatusFishDTO save(StatusFishDTO statusFishDTO) {
        statusFishRepository.save(createFromD(statusFishDTO));
        return statusFishDTO;
    }

    @Override
    public StatusFishDTO delete(Long statusFishId) {
        StatusFish statusFish = statusFishRepository.findById(statusFishId).get();
        try {
            statusFishRepository.delete(statusFish);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(StatusFish.class, statusFishId);
        }
        return createFromE(statusFish);
    }

    @Override
    public StatusFish createFromD(StatusFishDTO statusFishDTO) {
        StatusFish statusFish = modelMapper.map(statusFishDTO, StatusFish.class);
        return statusFish;
    }

    @Override
    public StatusFishDTO createFromE(StatusFish statusFish) {
        StatusFishDTO statusFishDTO = modelMapper.map(statusFish, StatusFishDTO.class);
        return statusFishDTO;
    }

    @Override
    public StatusFish updateEntity(StatusFish statusFish, StatusFishDTO statusFishDTO) {
        if (statusFish != null && statusFishDTO != null){
            statusFish.setStatusFishName(statusFishDTO.getStatusFishName());
        }
        return statusFish;
    }
}
