package com.n19dccn112.service;

import com.n19dccn112.model.dto.WardDTO;
import com.n19dccn112.model.entity.Ward;
import com.n19dccn112.repository.DistrictRepository;
import com.n19dccn112.repository.WardRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WardService implements IBaseService<WardDTO, Integer>, IModelMapper<WardDTO, Ward> {
    private final WardRepository wardRepository;
    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    public WardService(WardRepository wardRepository, DistrictRepository districtRepository, ModelMapper modelMapper) {
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WardDTO> findAll() {
        return createFromEntities(wardRepository.findAll());
    }

    public List<WardDTO> findAllByDistrictId(Integer districtId) {
        return createFromEntities(wardRepository.findAllByDistrict_DistrictsId(districtId));
    }

    @Override
    public WardDTO findById(Integer integer) {
        return createFromE(wardRepository.getById(integer));
    }

    @Override
    public WardDTO update(Integer integer, WardDTO wardDTO) {
        return null;
    }

    @Override
    public WardDTO save(WardDTO wardDTO) {
        return null;
    }

    @Override
    public WardDTO delete(Integer integer) {
        return null;
    }

    @Override
    public Ward createFromD(WardDTO dto) {
        Ward ward = modelMapper.map(dto, Ward.class);
        ward.setDistrict(districtRepository.getById(dto.getDistrictsId()));
        return ward;
    }

    @Override
    public WardDTO createFromE(Ward entity) {
        WardDTO wardDTO = modelMapper.map(entity, WardDTO.class);
        wardDTO.setDistrictsId(entity.getDistrict().getDistrictsId());
        return wardDTO;
    }

    @Override
    public Ward updateEntity(Ward entity, WardDTO dto) {
        return null;
    }
}
