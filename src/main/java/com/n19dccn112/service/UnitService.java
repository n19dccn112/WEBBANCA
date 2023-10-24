package com.n19dccn112.service;

import com.n19dccn112.model.dto.UnitDTO;
import com.n19dccn112.model.entity.Unit;
import com.n19dccn112.repository.UnitRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnitService implements IBaseService<UnitDTO, Long>, IModelMapper<UnitDTO, Unit> {
    private final UnitRepository unitRepository;
    private final ModelMapper modelMapper;

    public UnitService(UnitRepository unitRepository, ModelMapper modelMapper) {
        this.unitRepository = unitRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UnitDTO> findAll() {
        return createFromEntities(unitRepository.findAll());
    }

    public List<UnitDTO> findAllByProductId(Long productId){
        return createFromEntities(unitRepository.findAllByProductId(productId));
    }

    public List<UnitDTO> findAllByUnits(List<Long> unitIds){
        List<UnitDTO> unitDTOS = new ArrayList<>();
        for (Long unitId: unitIds){
            unitDTOS.add(findById(unitId));
        }
        return unitDTOS;
    }

    @Override
    public UnitDTO findById(Long unitId) {
        return createFromE(unitRepository.findById(unitId).get());
    }

    @Override
    public UnitDTO update(Long unitId, UnitDTO unitDTO) {
        Unit unit = unitRepository.findById(unitId).get();
        unitRepository.save(updateEntity(unit, unitDTO));
        return unitDTO;
    }

    @Override
    public UnitDTO save(UnitDTO unitDTO) {
        unitRepository.save(createFromD(unitDTO));
        return unitDTO;
    }

    @Override
    public UnitDTO delete(Long unitId) {
        Unit unit = unitRepository.findById(unitId).get();
        try {
            unitRepository.delete(unit);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Unit.class, unitId);
        }
        return createFromE(unit);
    }

    @Override
    public Unit createFromD(UnitDTO unitDTO) {
        Unit unit = modelMapper.map(unitDTO, Unit.class);
        return unit;
    }

    @Override
    public UnitDTO createFromE(Unit unit) {
        UnitDTO unitDTO = modelMapper.map(unit, UnitDTO.class);
        return unitDTO;
    }

    @Override
    public Unit updateEntity(Unit unit, UnitDTO unitDTO) {
        if (unit != null && unitDTO != null){
            unit.setUnitName(unitDTO.getUnitName());
            unit.setUnitDescription(unitDTO.getUnitDescription());
        }
        return unit;
    }
}
