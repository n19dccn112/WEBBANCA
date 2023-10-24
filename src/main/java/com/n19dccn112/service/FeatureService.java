package com.n19dccn112.service;

import com.n19dccn112.model.dto.FeatureDTO;
import com.n19dccn112.model.entity.Feature;
import com.n19dccn112.repository.FeatureRepository;
import com.n19dccn112.repository.FeatureTypeRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class FeatureService implements IBaseService<FeatureDTO, Long>, IModelMapper<FeatureDTO, Feature> {
    private final FeatureRepository featureRepository;
    private final FeatureTypeRepository featureTypeRepository;
    private final ModelMapper modelMapper;

    public FeatureService(FeatureRepository featureRepository, FeatureTypeRepository featureTypeRepository, ModelMapper modelMapper) {
        this.featureRepository = featureRepository;
        this.featureTypeRepository = featureTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FeatureDTO> findAll() {
        return createFromEntities(featureRepository.findAll());
    }

    public List<FeatureDTO> findAllFeatureTypeId(Long featureTypeId) {
        return createFromEntities(featureRepository.findAllByFeatureType_FeatureTypeId(featureTypeId));
    }

    @Override
    public FeatureDTO findById(Long featureId) {
        return createFromE(featureRepository.findById(featureId).get());
    }

    @Override
    public FeatureDTO update(Long featureId, FeatureDTO featureDTO) {
        Feature feature = featureRepository.findById(featureId).get();
        featureRepository.save(updateEntity(feature, featureDTO));
        return featureDTO;
    }

    @Override
    public FeatureDTO save(FeatureDTO featureDTO) {
        featureRepository.save(createFromD(featureDTO));
        return featureDTO;
    }

    @Override
    public FeatureDTO delete(Long featureId) {
        Feature feature = featureRepository.findById(featureId).get();
        try {
            featureRepository.delete(feature);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Feature.class, featureId);
        }
        return createFromE(feature);
    }

    @Override
    public Feature createFromD(FeatureDTO featureDTO) {
        Feature feature = modelMapper.map(featureDTO, Feature.class);
        feature.setFeatureType(featureTypeRepository.findById(featureDTO.getFeatureTypeId()).get());
        return feature;
    }

    @Override
    public FeatureDTO createFromE(Feature feature) {
        FeatureDTO featureDTO = modelMapper.map(feature, FeatureDTO.class);
        featureDTO.setFeatureTypeId(feature.getFeatureType().getFeatureTypeId());
        return featureDTO;
    }

    @Override
    public Feature updateEntity(Feature feature, FeatureDTO featureDTO) {
        if (feature != null && featureDTO != null){
            feature.setSpecific(featureDTO.getSpecific());
            feature.setFeatureType(featureTypeRepository.findById(featureDTO.getFeatureTypeId()).get());
        }
        return feature;
    }
}
