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
    private final StatusFishDetailService statusFishDetailService;
    private final UnitDetailRepository unitDetailRepository;
    private final StatusFishRepository statusFishRepository;
    private final ModelMapper modelMapper;

    public PondService(PondRepository pondRepository, StatusFishDetailRepository statusFishDetailRepository, StatusFishDetailService statusFishDetailService, UnitDetailRepository unitDetailRepository, StatusFishRepository statusFishRepository, ModelMapper modelMapper) {
        this.pondRepository = pondRepository;
        this.statusFishDetailRepository = statusFishDetailRepository;
        this.statusFishDetailService = statusFishDetailService;
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

    public PondDTO getPriceStand(PondDTO pondDTO){
        return pondDTO;
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
        int sumAmount = pondDTO.getPondAmount();
        int sumPriceShip = pondDTO.getPriceShip();
        int sumPriceStander = pondDTO.getStandardPrice();
        int sumSale = 0;
        try {
            Pond pond = pondRepository.findPondOld(pondDTO.getUnitDetailId());
            PondDTO pondDTOOld = createFromE(pond);
            sumSale = pond.getPondAmount() - pondDTOOld.getBenhAmount() - pondDTOOld.getSongAmount();
            int caTonTai = 0;
            caTonTai = pondDTOOld.getSongAmount() + pondDTOOld.getBenhAmount();
            sumAmount += caTonTai;
            float percentRemain = (float) (caTonTai) / pond.getPondAmount();
            int priceShip = Math.round(pond.getPriceShip() * percentRemain / 1000) * 1000;
            sumPriceShip += priceShip;
            int priceStandard = Math.round(pond.getStandardPrice() * percentRemain/ 1000) * 1000;
            sumPriceStander += priceStandard;
            pond.setPondAmount(sumSale + pondDTOOld.getChetAmount());
            pond.setPriceShip(pond.getPriceShip() - priceShip);
            pond.setStandardPrice(pond.getStandardPrice() - priceStandard);
            pondRepository.save(pond);
        }catch (Exception e){}
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
        statusFishDetailService.save(statusFishDetailService.createFromE(statusFishDetail));
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
        int amountBenh = 0;
        int amountChet = 0;
        int amountSong = 0;
        pondDTO.setUnitDetailId(pond.getUnitDetail().getUnitDetailId());
        try {
            amountBenh = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndIdChetOrSong(pond.getUnitDetail().getUnitDetailId(), 2L);
        }catch (Exception e){}
        try {
            amountChet = statusFishDetailRepository.findAllByUnitDetail_UnitDetailIdAndIdChetOrSong(pond.getUnitDetail().getUnitDetailId(), 3L);
        }catch (Exception e){}
        try {amountSong = pond.getPondAmount() - amountChet - amountBenh;
        }catch (Exception e){}
        pondDTO.setBenhAmount(amountBenh);
        pondDTO.setChetAmount(amountChet);
        pondDTO.setSongAmount(amountSong);
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
