package com.n19dccn112.service;

import com.n19dccn112.model.dto.UpdateDateStatusFishDetailDTO;
import com.n19dccn112.model.entity.UpdateDateStatusFishDetail;
import com.n19dccn112.repository.StatusFishDetailRepository;
import com.n19dccn112.repository.StatusFishRepository;
import com.n19dccn112.repository.UnitDetailRepository;
import com.n19dccn112.repository.UpdateDateStatusFishDetailRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UpdateDateStatusFishDetailService implements IBaseService<UpdateDateStatusFishDetailDTO, Long>, IModelMapper<UpdateDateStatusFishDetailDTO, UpdateDateStatusFishDetail> {
    private final ModelMapper modelMapper;
    private final UpdateDateStatusFishDetailRepository updateDateStatusFishDetailRepository;
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final StatusFishRepository statusFishRepository;

    public UpdateDateStatusFishDetailService(ModelMapper modelMapper, UpdateDateStatusFishDetailRepository updateDateStatusFishDetailRepository, StatusFishDetailService statusFishDetailService, StatusFishDetailRepository statusFishDetailRepository, UnitDetailRepository unitDetailRepository, StatusFishRepository statusFishRepository) {
        this.modelMapper = modelMapper;
        this.updateDateStatusFishDetailRepository = updateDateStatusFishDetailRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.statusFishRepository = statusFishRepository;
    }

    @Override
    public List<UpdateDateStatusFishDetailDTO> findAll() {
        return null;
    }

    public List<UpdateDateStatusFishDetailDTO> findAll(Long id, Date updateDate) {
        return createFromEntities(updateDateStatusFishDetailRepository.findAll(id, updateDate));
    }

    @Override
    public UpdateDateStatusFishDetailDTO findById(Long aLong) {
        return null;
    }

    @Override
    public UpdateDateStatusFishDetailDTO update(Long aLong, UpdateDateStatusFishDetailDTO updateDateStatusFishDetailDTO) {
        return null;
    }

    @Override
    public UpdateDateStatusFishDetailDTO save(UpdateDateStatusFishDetailDTO updateDateStatusFishDetailDTO) {
        updateDateStatusFishDetailRepository.save(createFromD(updateDateStatusFishDetailDTO));
        return updateDateStatusFishDetailDTO;
    }

    @Override
    public UpdateDateStatusFishDetailDTO delete(Long aLong) {
        return null;
    }

    @Override
    public UpdateDateStatusFishDetail createFromD(UpdateDateStatusFishDetailDTO dto) {
        UpdateDateStatusFishDetail updateDateStatusFishDetail = modelMapper.map(dto, UpdateDateStatusFishDetail.class);
        updateDateStatusFishDetail.setStatusFishDetail(statusFishDetailRepository.findById(dto.getUpdateDateStatusFishDetailId()).get());
        updateDateStatusFishDetail.setStatusFishFrom(statusFishRepository.findById(dto.getStatusFishId()).get());
        return updateDateStatusFishDetail;
    }

    @Override
    public UpdateDateStatusFishDetailDTO createFromE(UpdateDateStatusFishDetail entity) {
        UpdateDateStatusFishDetailDTO updateDateStatusFishDetailDTO = modelMapper.map(entity, UpdateDateStatusFishDetailDTO.class);
        updateDateStatusFishDetailDTO.setStatusFishId(entity.getStatusFishFrom().getStatusFishId());
        updateDateStatusFishDetailDTO.setStatusDetailIdFrom(entity.getStatusFishDetail().getStatusFishDetailId());
        return updateDateStatusFishDetailDTO;
    }

    @Override
    public UpdateDateStatusFishDetail updateEntity(UpdateDateStatusFishDetail entity, UpdateDateStatusFishDetailDTO dto) {
        return null;
    }
}
