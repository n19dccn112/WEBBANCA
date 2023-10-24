package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureTypeDTO;
import com.n19dccn112.model.entity.FeatureType;
import com.n19dccn112.repository.FeatureTypeRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class FeatureTypeService implements IBaseService<FeatureTypeDTO, Long>, IModelMapper<FeatureTypeDTO, FeatureType> {
    private final FeatureTypeRepository featureTypeRepository;
    private final ModelMapper modelMapper;

    public FeatureTypeService(FeatureTypeRepository featureTypeRepository, ModelMapper modelMapper) {
        this.featureTypeRepository = featureTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeatureTypeDTO> findAll() {
        return createFromEntities(featureTypeRepository.findAll());
    }

    @Override
    public FeatureTypeDTO findById(Long featureTypeId) {
        return createFromE(featureTypeRepository.findById(featureTypeId).get());
    }

    @Override
    public FeatureTypeDTO update(Long featureTypeId, FeatureTypeDTO featureTypeDTO) {
        FeatureType featureType = featureTypeRepository.findById(featureTypeId).get();
        featureTypeRepository.save(updateEntity(featureType, featureTypeDTO));
        return featureTypeDTO;
    }

    @Override
    public FeatureTypeDTO save(FeatureTypeDTO featureTypeDTO) {
        featureTypeRepository.save(createFromD(featureTypeDTO));
        return featureTypeDTO;
    }

    @Override
    public FeatureTypeDTO delete(Long featureTypeId) {
        FeatureType featureType = featureTypeRepository.findById(featureTypeId).get();
        try {
            featureTypeRepository.delete(featureType);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(FeatureType.class, featureTypeId);
        }
        return createFromE(featureType);
    }

    @Override
    public FeatureType createFromD(FeatureTypeDTO featureTypeDTO) {
        FeatureType featureType = modelMapper.map(featureTypeDTO, FeatureType.class);
        return featureType;
    }

    @Override
    public FeatureTypeDTO createFromE(FeatureType featureType) {
        FeatureTypeDTO featureTypeDTO = modelMapper.map(featureType, FeatureTypeDTO.class);
        return featureTypeDTO;
    }

    @Override
    public FeatureType updateEntity(FeatureType featureType, FeatureTypeDTO featureTypeDTO) {
        if (featureType != null && featureTypeDTO != null){
            featureType.setFeatureTypeName(featureTypeDTO.getFeatureTypeName());
            featureType.setFeatureTypeUnit(featureTypeDTO.getFeatureTypeUnit());
            featureType.setIsShow(featureType.getIsShow());
        }
        return featureType;
    }
}
