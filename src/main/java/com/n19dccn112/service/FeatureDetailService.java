package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureDetailDTO;
import com.n19dccn112.model.entity.FeatureDetail;
import com.n19dccn112.repository.FeatureDetailRepository;
import com.n19dccn112.repository.FeatureRepository;
import com.n19dccn112.repository.UnitDetailRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class FeatureDetailService implements IBaseService<FeatureDetailDTO, Long>, IModelMapper<FeatureDetailDTO, FeatureDetail> {
    private final FeatureDetailRepository featureDetailRepository;
    private final FeatureRepository featureRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final ModelMapper modelMapper;

    public FeatureDetailService(FeatureDetailRepository featureDetailRepository, FeatureRepository featureRepository, UnitDetailRepository unitDetailRepository, ModelMapper modelMapper) {
        this.featureDetailRepository = featureDetailRepository;
        this.featureRepository = featureRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeatureDetailDTO> findAll() {
        return createFromEntities(featureDetailRepository.findAll());
    }

    public List<FeatureDetailDTO> findAllUnitDetailId(Long unitDetailId) {
        return createFromEntities(featureDetailRepository.findAllByUnitDetail_UnitDetailId(unitDetailId));
    }

    public List<FeatureDetailDTO> findAllFeatureId(Long featureId) {
        return createFromEntities(featureDetailRepository.findAllByFeature_FeatureId(featureId));
    }

    public List<FeatureDetailDTO> findAlFeatureIdAndUnitDetailId(Long featureId, Long unitDetailId) {
        return createFromEntities(featureDetailRepository.findAllByFeature_FeatureIdAndUnitDetail_UnitDetailId(featureId, unitDetailId));
    }
    
    @Override
    public FeatureDetailDTO findById(Long featureDetailId) {
        return createFromE(featureDetailRepository.findById(featureDetailId).get());
    }

    @Override
    public FeatureDetailDTO update(Long featureDetailId, FeatureDetailDTO featureDetailDTO) {
        FeatureDetail featureDetail = featureDetailRepository.findById(featureDetailId).get();
        featureDetailRepository.save(updateEntity(featureDetail, featureDetailDTO));
        return featureDetailDTO;
    }

    @Override
    public FeatureDetailDTO save(FeatureDetailDTO featureDetailDTO) {
        featureDetailRepository.save(createFromD(featureDetailDTO));
        return featureDetailDTO;
    }

    @Override
    public FeatureDetailDTO delete(Long featureDetailId) {
        FeatureDetail featureDetail = featureDetailRepository.findById(featureDetailId).get();
        try {
            featureDetailRepository.save(featureDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(FeatureDetail.class, featureDetailId);
        }
        return createFromE(featureDetail);
    }

    @Override
    public FeatureDetail createFromD(FeatureDetailDTO featureDetailDTO) {
        FeatureDetail featureDetail = modelMapper.map(featureDetailDTO, FeatureDetail.class);
        featureDetail.setFeature(featureRepository.findById(featureDetailDTO.getFeatureId()).get());
        featureDetail.setUnitDetail(unitDetailRepository.findById(featureDetailDTO.getUnitDetailId()).get());
        featureDetail.setImportance(featureDetailDTO.getImportance());
        return featureDetail;
    }

    @Override
    public FeatureDetailDTO createFromE(FeatureDetail featureDetail) {
        FeatureDetailDTO featureDetailDTO = modelMapper.map(featureDetail, FeatureDetailDTO.class);
        featureDetailDTO.setFeatureId(featureDetail.getFeature().getFeatureId());
        featureDetailDTO.setUnitDetailId(featureDetail.getUnitDetail().getUnitDetailId());
        featureDetailDTO.setImportance(featureDetail.getImportance());
        return featureDetailDTO;
    }

    @Override
    public FeatureDetail updateEntity(FeatureDetail featureDetail, FeatureDetailDTO featureDetailDTO) {
        if (featureDetail != null && featureDetailDTO != null){
            if (featureDetailDTO.getFeaturePrice() != null){
                featureDetail.setFeaturePrice(featureDetailDTO.getFeaturePrice());
            }
            if (featureDetailDTO.getFeatureId() != null){
                featureDetail.setFeature(featureRepository.findById(featureDetailDTO.getFeatureId()).get());
            }
            if (featureDetailDTO.getUnitDetailId() != null){
                featureDetail.setUnitDetail(unitDetailRepository.findById(featureDetailDTO.getUnitDetailId()).get());
            }
            if(featureDetailDTO.getImportance() != null) {
                featureDetail.setImportance(featureDetailDTO.getImportance());
            }
        }
        return featureDetail;
    }
}
