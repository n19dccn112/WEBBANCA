package com.n19dccn112.service;

import com.n19dccn112.model.dto.UnitDetailDTO;
import com.n19dccn112.model.entity.Pond;
import com.n19dccn112.model.entity.StatusFishDetail;
import com.n19dccn112.model.entity.UnitDetail;
import com.n19dccn112.repository.*;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class UnitDetailService implements IBaseService<UnitDetailDTO, Long>, IModelMapper<UnitDetailDTO, UnitDetail> {
    private final UnitDetailRepository unitDetailRepository;
    private final UnitRepository unitRepository;
    private final ProductRepository productRepository;
    private final PondRepository pondRepository;
    private final StatusFishRepository statusFishRepository;

    private final StatusFishDetailRepository statusFishDetailRepository;
    private final ModelMapper modelMapper;

    public UnitDetailService(UnitDetailRepository unitDetailRepository, UnitRepository unitRepository, ProductRepository productRepository, PondRepository pondRepository, StatusFishDetailRepository statusFishDetailRepository, StatusFishRepository sta, StatusFishDetailRepository statusFishDetailRepository1, ModelMapper modelMapper) {
        this.unitDetailRepository = unitDetailRepository;
        this.unitRepository = unitRepository;
        this.productRepository = productRepository;
        this.pondRepository = pondRepository;
        this.statusFishRepository = sta;
        this.statusFishDetailRepository = statusFishDetailRepository1;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UnitDetailDTO> findAll() {
        return createFromEntities(unitDetailRepository.findAll());
    }

    public List<UnitDetailDTO> findAllByUnitDetailIds(List<Long> unitDetailIds){
        List<UnitDetailDTO> unitDetailDTOS = new ArrayList<>();
        for (Long unitDetailId: unitDetailIds){
            unitDetailDTOS.add(findById(unitDetailId));
        }
        return unitDetailDTOS;
    }

    public UnitDetailDTO findByUserDetailIdNewSave(Long unitId, Long productId){
        return createFromE(unitDetailRepository.findById(unitDetailRepository.userDetailIdNewSave(unitId, productId)).get());
    }

    public List<UnitDetailDTO> findAllByProductId(Long productId) {
        return createFromEntities(unitDetailRepository.findAllByProduct_ProductId(productId));
    }

    public List<UnitDetailDTO> findAllByUnitId(Long unitId) {
        return createFromEntities(unitDetailRepository.findAllByUnit_UnitId(unitId));
    }

    public List<UnitDetailDTO> findAllByUnitIdAndProductId(Long unitId, Long productId) {
        return createFromEntities(unitDetailRepository.findAllByUnit_UnitIdAndProduct_ProductId(unitId, productId));
    }

    public List<UnitDetailDTO> findAllByUnitIdsAndProductId(List<Long> unitIds, Long productId){
        List<UnitDetailDTO> unitDetailDTOS = new ArrayList<>();
        for (Long unitId: unitIds){
            unitDetailDTOS.addAll(findAllByUnitIdAndProductId(unitId, productId));
        }
        return unitDetailDTOS;
    }

    @Override
    public UnitDetailDTO findById(Long unitDetailId) {
        return createFromE(unitDetailRepository.findById(unitDetailId).get());
    }

    @Override
    public UnitDetailDTO update(Long unitDetailId, UnitDetailDTO unitDetailDTO) {
        UnitDetail unitDetail = unitDetailRepository.findById(unitDetailId).get();
        unitDetailRepository.save(updateEntity(unitDetail, unitDetailDTO));
        return unitDetailDTO;
    }

    @Override
    public UnitDetailDTO save(UnitDetailDTO unitDetailDTO) {
        unitDetailRepository.save(createFromD(unitDetailDTO));
        Long unitDetailId = unitDetailRepository.userDetailIdNewSave(unitDetailDTO.getUnitId(), unitDetailDTO.getProductId());
        StatusFishDetail statusFishDetail = new StatusFishDetail();
        statusFishDetail.setUnitDetail(unitDetailRepository.findById(unitDetailId).get());
        statusFishDetail.setStatusFish(statusFishRepository.findById(1L).get());
        statusFishDetail.setAmount(unitDetailDTO.getUnitDetailAmount());
        statusFishDetailRepository.save(statusFishDetail);
        return unitDetailDTO;
    }

    @Override
    public UnitDetailDTO delete(Long unitDetailId) {
        UnitDetail unitDetail = unitDetailRepository.findById(unitDetailId).get();
        try {
            unitDetailRepository.delete(unitDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(UnitDetail.class, unitDetailId);
        }
        return createFromE(unitDetail);
    }

    @Override
    public UnitDetail createFromD(UnitDetailDTO unitDetailDTO) {
        UnitDetail unitDetail = modelMapper.map(unitDetailDTO, UnitDetail.class);
        unitDetail.setProduct(productRepository.findById(unitDetailDTO.getProductId()).get());
        unitDetail.setUnit(unitRepository.findById(unitDetailDTO.getUnitId()).get());
        return unitDetail;
    }

    @Override
    public UnitDetailDTO createFromE(UnitDetail unitDetail) {
        UnitDetailDTO unitDetailDTO = modelMapper.map(unitDetail, UnitDetailDTO.class);
        unitDetailDTO.setProductId(unitDetail.getProduct().getProductId());
        unitDetailDTO.setUnitId(unitDetail.getUnit().getUnitId());
        int amountProduct = 0;
        try {
            StatusFishDetail statusFishDetail =
                    statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(unitDetail.getUnitDetailId(), 1L).get(0);
            amountProduct = statusFishDetail.getAmount();
        }
        catch (Exception e){}
        unitDetailDTO.setUnitDetailAmount(amountProduct);
        return unitDetailDTO;
    }

    @Override
    public UnitDetail updateEntity(UnitDetail unitDetail, UnitDetailDTO unitDetailDTO) {
        if (unitDetail != null && unitDetailDTO != null){
            if (unitDetailDTO.getLength() >= 0L){
                unitDetail.setLength(unitDetailDTO.getLength());
            }
            if (unitDetailDTO.getSpeedGrowth() >= 0L){
                unitDetail.setSpeedGrowth(unitDetailDTO.getSpeedGrowth());
            }
            if (unitDetailDTO.getProductPrice() >= 0){
                unitDetail.setProductPrice(unitDetailDTO.getProductPrice());
            }
            if (unitDetailDTO.getStatusFishIdAmount() != null){
                Enumeration<Long> keys = unitDetailDTO.getStatusFishIdAmount().keys();
                while (keys.hasMoreElements()) {
                    Long id = keys.nextElement();
                    Integer amount = unitDetailDTO.getStatusFishIdAmount().get(id);
                    StatusFishDetail statusFishDetail = new StatusFishDetail();
                    statusFishDetail.setUnitDetail(unitDetailRepository.findById(unitDetailDTO.getUnitDetailId()).get());
                    statusFishDetail.setUnitDetail(unitDetailRepository.findById(id).get());
                    statusFishDetail.setAmount(amount);
                    try {
                        StatusFishDetail statusFishDetailUpdate = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(unitDetailDTO.getUnitDetailId(), id).get(0);statusFishDetailRepository.save(statusFishDetail);
                        statusFishDetail.setStatusFishDetailId(statusFishDetailUpdate.getStatusFishDetailId());
                    }catch (Exception e){}
                    statusFishDetailRepository.save(statusFishDetail);
                }
            }
        }
        return unitDetail;
    }
}