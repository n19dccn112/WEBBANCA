package com.n19dccn112.service;

import com.n19dccn112.model.dto.StatusFishDetailDTO;
import com.n19dccn112.model.entity.StatusFishDetail;
import com.n19dccn112.model.entity.UpdateDateStatusFishDetail;
import com.n19dccn112.repository.StatusFishDetailRepository;
import com.n19dccn112.repository.StatusFishRepository;
import com.n19dccn112.repository.UnitDetailRepository;
import com.n19dccn112.repository.UpdateDateStatusFishDetailRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatusFishDetailService implements IBaseService<StatusFishDetailDTO, Long>, IModelMapper<StatusFishDetailDTO, StatusFishDetail> {
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final StatusFishRepository statusFishRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final UpdateDateStatusFishDetailRepository updateDateStatusFishDetailRepository;
    private final ModelMapper modelMapper;

    public StatusFishDetailService(StatusFishDetailRepository statusFishDetailRepository, StatusFishRepository statusFishRepository, UnitDetailRepository unitDetailRepository, UpdateDateStatusFishDetailRepository updateDateStatusFishDetailRepository, ModelMapper modelMapper) {
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.statusFishRepository = statusFishRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.updateDateStatusFishDetailRepository = updateDateStatusFishDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StatusFishDetailDTO> findAll() {
        return createFromEntities(statusFishDetailRepository.findAll());
    }
    public List<StatusFishDetailDTO> findAllByStatusFishId(Long statusFishId) {
        return createFromEntities(statusFishDetailRepository.findAllByStatusFish_StatusFishId(statusFishId));
    }
    public List<StatusFishDetailDTO> findAllUnitDetailId(Long unitDetailId) {
        return createFromEntities(statusFishDetailRepository.findAllByUnitDetail_UnitDetailId(unitDetailId));
    }
    public List<StatusFishDetailDTO> findAllUnitDetailIdAndStatusFishId(Long unitDetailId, Long statusFishId) {
        return createFromEntities(statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(unitDetailId, statusFishId));
    }

    @Override
    public StatusFishDetailDTO findById(Long statusFishDetailId) {
        return createFromE(statusFishDetailRepository.findById(statusFishDetailId).get());
    }

    @Override
    public StatusFishDetailDTO update(Long statusFishDetailId, StatusFishDetailDTO statusFishDetailDTO) {
        StatusFishDetail statusFishDetail = statusFishDetailRepository.findById(statusFishDetailId).get();
        int amount = statusFishDetailDTO.getAmount() - statusFishDetail.getAmount();
        statusFishDetailRepository.save(updateEntity(statusFishDetail, statusFishDetailDTO));

        UpdateDateStatusFishDetail updateDateStatusFishDetail = new UpdateDateStatusFishDetail();
        updateDateStatusFishDetail.setStatusFishDetail(statusFishDetailRepository.findById(statusFishDetailId).get());
        updateDateStatusFishDetail.setUpdateDate(new Date());
        updateDateStatusFishDetail.setAmount(amount);
        updateDateStatusFishDetail.setStatusFishFrom(statusFishDetailRepository.findById(statusFishDetailId).get().getStatusFish());
        updateDateStatusFishDetailRepository.save(updateDateStatusFishDetail);
        return statusFishDetailDTO;
    }

    @Override
    public StatusFishDetailDTO save(StatusFishDetailDTO statusFishDetailDTO) {
        statusFishDetailDTO.setDateUpdate(new Date());
        statusFishDetailRepository.save(createFromD(statusFishDetailDTO));
        Long statusFishDetailId = statusFishDetailRepository.statusFishDetailIdNewSave(statusFishDetailDTO.getUnitDetailId(), statusFishDetailDTO.getStatusFishId());

        UpdateDateStatusFishDetail updateDateStatusFishDetail = new UpdateDateStatusFishDetail();
        updateDateStatusFishDetail.setStatusFishDetail(statusFishDetailRepository.findById(statusFishDetailId).get());
        updateDateStatusFishDetail.setUpdateDate(new Date());
        updateDateStatusFishDetail.setAmount(statusFishDetailDTO.getAmount());
        updateDateStatusFishDetailRepository.save(updateDateStatusFishDetail);
        return statusFishDetailDTO;
    }

    public void saveAll(List<StatusFishDetail> statusFishDetails) {
        for (StatusFishDetail statusFishDetail: statusFishDetails){
            save(createFromE(statusFishDetail));
        }
    }

    @Override
    public StatusFishDetailDTO delete(Long statusFishDetailId) {
        StatusFishDetail statusFishDetail = statusFishDetailRepository.findById(statusFishDetailId).get();
        try {
            statusFishDetailRepository.delete(statusFishDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(StatusFishDetail.class, statusFishDetailId);
        }
        return createFromE(statusFishDetail);
    }

    @Override
    public StatusFishDetail createFromD(StatusFishDetailDTO statusFishDetailDTO) {
        StatusFishDetail statusFishDetail = modelMapper.map(statusFishDetailDTO, StatusFishDetail.class);
        statusFishDetail.setStatusFish(statusFishRepository.findById(statusFishDetailDTO.getStatusFishId()).get());
        statusFishDetail.setUnitDetail(unitDetailRepository.findById(statusFishDetailDTO.getUnitDetailId()).get());
        return statusFishDetail;
    }

    @Override
    public StatusFishDetailDTO createFromE(StatusFishDetail statusFishDetail) {
        StatusFishDetailDTO statusFishDetailDTO = modelMapper.map(statusFishDetail, StatusFishDetailDTO.class);
        statusFishDetailDTO.setStatusFishId(statusFishDetail.getStatusFish().getStatusFishId());
        statusFishDetailDTO.setUnitDetailId(statusFishDetail.getUnitDetail().getUnitDetailId());
        return statusFishDetailDTO;
    }

    @Override
    public StatusFishDetail updateEntity(StatusFishDetail statusFishDetail, StatusFishDetailDTO statusFishDetailDTO) {
        if (statusFishDetail != null && statusFishDetailDTO != null){
            statusFishDetail.setDateUpdate(new Date());
            statusFishDetail.setAmount(statusFishDetailDTO.getAmount());
        }
        return statusFishDetail;
    }
}
