package com.n19dccn112.service;

import com.n19dccn112.model.dto.PondDTO;
import com.n19dccn112.model.entity.Pond;
import com.n19dccn112.repository.PondRepository;
import com.n19dccn112.repository.StatusFishDetailRepository;
import com.n19dccn112.repository.StatusFishRepository;
import com.n19dccn112.repository.UnitDetailRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
@Service
public class PondService implements IBaseService<PondDTO, Long>, IModelMapper<PondDTO, Pond> {
    private final PondRepository pondRepository;
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final ModelMapper modelMapper;

    public PondService(PondRepository pondRepository, StatusFishDetailRepository statusFishDetailRepository, UnitDetailRepository unitDetailRepository, ModelMapper modelMapper) {
        this.pondRepository = pondRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PondDTO> findAll() {
        return createFromEntities(pondRepository.findAll());
    }

    public List<PondDTO> findAllByUnitDetailId(Long unitDetailId){
        return createFromEntities(pondRepository.findAllByUnitDetail_UnitDetailId(unitDetailId));
    }

    @Override
    public PondDTO findById(Long pondId) {
        return createFromE(pondRepository.findById(pondId).get());
    }

    @Override
    public PondDTO update(Long pondId, PondDTO pondDTO) {
        Pond pond = pondRepository.findById(pondId).get();
        pondRepository.save(updateEntity(pond, pondDTO));
        return pondDTO;
    }

    @Override
    public PondDTO save(PondDTO pondDTO) {
        pondRepository.save(createFromD(pondDTO));
        return pondDTO;
    }

    @Override
    public PondDTO delete(Long pondId) {
        Pond pond = pondRepository.findById(pondId).get();
        try {
            pondRepository.delete(pond);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Pond.class, pondId);
        }
        return createFromE(pond);
    }

    @Override
    public Pond createFromD(PondDTO pondDTO) {
        Pond pond = modelMapper.map(pondDTO, Pond.class);
        pond.setUnitDetail(unitDetailRepository.findById(pondDTO.getUnitDetailId()).get());
        return pond;
    }

    @Override
    public PondDTO createFromE(Pond pond) {
        PondDTO pondDTO = modelMapper.map(pond, PondDTO.class);
        pondDTO.setUnitDetailId(pond.getUnitDetail().getUnitDetailId());
        return pondDTO;
    }

    @Override
    public Pond updateEntity(Pond pond, PondDTO pondDTO) {
        if (pond != null && pondDTO != null){
            if (pondDTO.getUnitDetailId() != null){
                pond.setUnitDetail(unitDetailRepository.findById(pondDTO.getUnitDetailId()).get());
            }
            if (pondDTO.getPondAmount() != null){
                pond.setPondAmount(pondDTO.getPondAmount());
            }
            if (pondDTO.getPondArea() != null){
                pond.setPondArea(pondDTO.getPondArea());
            }
            if (pondDTO.getStandardPrice() != null){
                pond.setStandardPrice(pondDTO.getStandardPrice());
            }
            if (pondDTO.getPondName() != null){
                pond.setPondName(pondDTO.getPondName());
            }

        }
        return pond;
    }
}
