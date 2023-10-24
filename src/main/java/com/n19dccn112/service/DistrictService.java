package com.n19dccn112.service;

import com.n19dccn112.model.dto.DistrictDTO;
import com.n19dccn112.model.entity.District;
import com.n19dccn112.repository.DistrictRepository;
import com.n19dccn112.repository.ProvincesRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService implements IBaseService<DistrictDTO, Integer>, IModelMapper<DistrictDTO, District> {
    private final DistrictRepository districtRepository;
    private final ProvincesRepository provincesRepository;
    private final ModelMapper modelMapper;

    public DistrictService(DistrictRepository districtRepository, ProvincesRepository provincesRepository, ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.provincesRepository = provincesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DistrictDTO> findAll() {
        return createFromEntities(districtRepository.findAll());
    }

    public List<DistrictDTO> findAllByProvinces(Integer provincesId) {
        return createFromEntities(districtRepository.findAllByProvinces_ProvincesId(provincesId));
    }

    @Override
    public DistrictDTO findById(Integer districtId) {
        return createFromE(districtRepository.getById(districtId));
    }

    @Override
    public DistrictDTO update(Integer integer, DistrictDTO districtDTO) {
        return null;
    }

    @Override
    public DistrictDTO save(DistrictDTO districtDTO) {
        return null;
    }

    @Override
    public DistrictDTO delete(Integer integer) {
        return null;
    }

    @Override
    public District createFromD(DistrictDTO districtDTO) {
        District district = modelMapper.map(districtDTO, District.class);
        district.setProvinces(provincesRepository.getById(districtDTO.getDistrictsId()));
        return district;
    }

    @Override
    public DistrictDTO createFromE(District district) {
        DistrictDTO districtDTO = modelMapper.map(district, DistrictDTO.class);
        districtDTO.setProvincesId(district.getProvinces().getProvincesId());
        return districtDTO;
    }

    @Override
    public District updateEntity(District entity, DistrictDTO dto) {
        return null;
    }
}
