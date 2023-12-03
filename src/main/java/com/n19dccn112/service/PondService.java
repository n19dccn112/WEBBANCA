package com.n19dccn112.service;

import com.n19dccn112.model.dto.PondDTO;
import com.n19dccn112.model.entity.Pond;
import com.n19dccn112.model.entity.StatusFishDetail;
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
import java.util.Date;
import java.util.List;
@Service
public class PondService implements IBaseService<PondDTO, Long>, IModelMapper<PondDTO, Pond> {
    private final PondRepository pondRepository;
    private final StatusFishDetailRepository statusFishDetailRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final StatusFishRepository statusFishRepository;
    private final ModelMapper modelMapper;

    public PondService(PondRepository pondRepository, StatusFishDetailRepository statusFishDetailRepository, UnitDetailRepository unitDetailRepository, StatusFishRepository statusFishRepository, ModelMapper modelMapper) {
        this.pondRepository = pondRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.unitDetailRepository = unitDetailRepository;
        this.statusFishRepository = statusFishRepository;
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
        List<Pond> ponds = pondRepository.findAllByUnitDetail_UnitDetailId(pondDTO.getUnitDetailId());
        int sumAmount = pondDTO.getPondAmount();
        int sumPriceShip = pondDTO.getPriceShip();
        int sumPriceStander = pondDTO.getStandardPrice();
        int sumLife = 0;
        int sumDeath = 0;
        int sumDisease = 0;
        try {
            sumLife = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(pondDTO.getUnitDetailId(), 1L).get(0).getAmount();
        }catch (Exception e){}
        try {
        sumDeath = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(pondDTO.getUnitDetailId(), 2L).get(0).getAmount();
        }catch (Exception e){}
        try {
        sumDisease = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(pondDTO.getUnitDetailId(), 3L).get(0).getAmount();
        }catch (Exception e){}
        int sumSale = pondDTO.getPondAmount() - sumDeath - sumDisease - sumLife;
        int caTonTai = 0;
        for (Pond pond: ponds){
            caTonTai = pond.getPondAmount() - sumSale - sumDeath;
            sumAmount += caTonTai;
            float percentRemain = (float) (caTonTai) / pond.getPondAmount();
            int priceShip = Math.round(pond.getPriceShip() * percentRemain / 1000) * 1000;
            sumPriceShip += priceShip;
            int priceStandard = Math.round(pond.getStandardPrice() * percentRemain/ 1000) * 1000;
            sumPriceStander += priceStandard;
            pond.setPondAmount(sumSale + sumDeath);
            pond.setPriceShip(pond.getPriceShip() - priceShip);
            pond.setStandardPrice(pond.getStandardPrice() - priceStandard);
            pondRepository.save(pond);
            break;
        }
        pondDTO.setPondAmount(sumAmount);
        pondDTO.setPriceShip(sumPriceShip);
        pondDTO.setStandardPrice(sumPriceStander);
        pondDTO.setInputDate(new Date());
        pondRepository.save(createFromD(pondDTO));

        StatusFishDetail statusFishDetail = new StatusFishDetail();
        try {
            statusFishDetail = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndAndStatusFish_StatusFishId(pondDTO.getUnitDetailId(), 1L).get(0);
        }catch (Exception e){
            statusFishDetail.setStatusFish(statusFishRepository.findById(1L).get());
            statusFishDetail.setUnitDetail(unitDetailRepository.findById(pondDTO.getUnitDetailId()).get());
        }
        statusFishDetail.setDateUpdate(new Date());
        int statusAmount = statusFishDetail.getAmount();
        statusFishDetail.setAmount(statusAmount + sumAmount);
        statusFishDetailRepository.save(statusFishDetail);
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
            if (pondDTO.getInputDate() != null){
                pond.setInputDate(pondDTO.getInputDate());
            }
            if (pondDTO.getStandardPrice() != null){
                pond.setStandardPrice(pondDTO.getStandardPrice());
            }
            if (pondDTO.getPriceShip() >= 0) {
                pond.setPriceShip(pondDTO.getPriceShip());
            }
        }
        return pond;
    }
}
