package com.n19dccn112.service;

import com.n19dccn112.model.dto.BusinessDTO;
import com.n19dccn112.model.entity.Business;
import com.n19dccn112.repository.BusinessRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class BusinessService implements IBaseService<BusinessDTO, Long>, IModelMapper<BusinessDTO, Business> {
    private final BusinessRepository businessRepository;
    private final ModelMapper modelMapper;

    public BusinessService(BusinessRepository businessRepository, ModelMapper modelMapper) {
        this.businessRepository = businessRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BusinessDTO> findAll() {
        return createFromEntities(businessRepository.findAll());
    }

    @Override
    public BusinessDTO findById(Long businessId) {
        return createFromE(businessRepository.findById(businessId).get());
    }

    @Override
    public BusinessDTO update(Long businessId, BusinessDTO businessDTO) {
        Business business = businessRepository.findById(businessId).get();
        businessRepository.save(updateEntity(business, businessDTO));
        return createFromE(business);
    }

    @Override
    public BusinessDTO save(BusinessDTO businessDTO) {
        businessRepository.save(createFromD(businessDTO));
        return businessDTO;
    }

    @Override
    public BusinessDTO delete(Long businessId) {
        Business business = businessRepository.findById(businessId).get();
        try {
            businessRepository.delete(business);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Business.class, businessId);
        }
        return createFromE(business);
    }

    @Override
    public Business createFromD(BusinessDTO businessDTO) {
        Business business = modelMapper.map(businessDTO, Business.class);
        return business;
    }

    @Override
    public BusinessDTO createFromE(Business business) {
        BusinessDTO businessDTO = modelMapper.map(business, BusinessDTO.class);
        return businessDTO;
    }

    @Override
    public Business updateEntity(Business business, BusinessDTO businessDTO) {
        if (business != null && businessDTO != null){
            business.setDesiredProfit(businessDTO.getDesiredProfit());
        }
        return business;
    }
}
