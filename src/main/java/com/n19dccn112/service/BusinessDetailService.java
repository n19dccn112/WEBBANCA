package com.n19dccn112.service;

import com.n19dccn112.model.dto.BusinessDetailDTO;
import com.n19dccn112.model.entity.BusinessDetail;
import com.n19dccn112.repository.BusinessDetailRepository;
import com.n19dccn112.repository.BusinessRepository;
import com.n19dccn112.repository.ProductRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class BusinessDetailService implements IBaseService<BusinessDetailDTO, Long>, IModelMapper<BusinessDetailDTO, BusinessDetail> {
    private final BusinessDetailRepository businessDetailRepository;
    private final BusinessRepository businessRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public BusinessDetailService(BusinessDetailRepository businessDetailRepository, BusinessRepository businessRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.businessDetailRepository = businessDetailRepository;
        this.businessRepository = businessRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BusinessDetailDTO> findAll() {
        return createFromEntities(businessDetailRepository.findAll());
    }

    public List<BusinessDetailDTO> findAllBusinessId(Long businessId) {
        return createFromEntities(businessDetailRepository.findAllByBusiness_BusinessId(businessId));
    }

    public List<BusinessDetailDTO> findAllProductId(Long productId) {
        return createFromEntities(businessDetailRepository.findAllByProduct_ProductId(productId));
    }

    public List<BusinessDetailDTO> findAllBusinessIdAndProductId(Long businessId, Long productId) {
        return createFromEntities(businessDetailRepository.findAllByBusiness_BusinessIdAndProduct_ProductId(businessId, productId));
    }

    @Override
    public BusinessDetailDTO findById(Long businessId) {
        return createFromE(businessDetailRepository.getById(businessId));
    }

    @Override
    public BusinessDetailDTO update(Long businessId, BusinessDetailDTO businessDetailDTO) {
        BusinessDetail businessDetail = businessDetailRepository.findById(businessId).get();
        businessDetailRepository.save(updateEntity(businessDetail, businessDetailDTO));
        return businessDetailDTO;
    }

    @Override
    public BusinessDetailDTO save(BusinessDetailDTO businessDetailDTO) {
        businessDetailRepository.save(createFromD(businessDetailDTO));
        return businessDetailDTO;
    }

    @Override
    public BusinessDetailDTO delete(Long businessId) {
        BusinessDetail businessDetail = businessDetailRepository.findById(businessId).get();
        try{
            businessDetailRepository.delete(businessDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(BusinessDetail.class, businessId);
        }
        return createFromE(businessDetail);
    }

    @Override
    public BusinessDetail createFromD(BusinessDetailDTO businessDetailDTO) {
        BusinessDetail businessDetail = modelMapper.map(businessDetailDTO, BusinessDetail.class);
        businessDetail.setBusiness(businessRepository.findById(businessDetailDTO.getBusinessId()).get());
        businessDetail.setProduct(productRepository.findById(businessDetailDTO.getProductId()).get());
        return businessDetail;
    }

    @Override
    public BusinessDetailDTO createFromE(BusinessDetail businessDetail) {
        BusinessDetailDTO businessDetailDTO = modelMapper.map(businessDetail, BusinessDetailDTO.class);
        businessDetailDTO.setBusinessId(businessDetail.getBusiness().getBusinessId());
        businessDetailDTO.setProductId(businessDetail.getProduct().getProductId());
        return businessDetailDTO;
    }

    @Override
    public BusinessDetail updateEntity(BusinessDetail businessDetail, BusinessDetailDTO businessDetailDTO) {
        if (businessDetail != null && businessDetailDTO !=null){
            businessDetail.setBusinessDateUpdate(businessDetailDTO.getBusinessDateUpdate());
            businessDetail.setBusiness(businessRepository.findById(businessDetailDTO.getBusinessId()).get());
            businessDetail.setProduct(productRepository.findById(businessDetailDTO.getProductId()).get());
        }
        return businessDetail;
    }
}
