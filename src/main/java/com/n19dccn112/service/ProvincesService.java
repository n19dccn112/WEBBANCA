package com.n19dccn112.service;

import com.n19dccn112.model.dto.ProvincesDTO;
import com.n19dccn112.model.entity.Provinces;
import com.n19dccn112.repository.ProvincesRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvincesService implements IBaseService<ProvincesDTO, Integer>, IModelMapper<ProvincesDTO, Provinces> {
    private final ProvincesRepository provincesRepository;
    private final ModelMapper modelMapper;

    public ProvincesService(ProvincesRepository provincesRepository, ModelMapper modelMapper) {
        this.provincesRepository = provincesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProvincesDTO> findAll() {
        return createFromEntities(provincesRepository.findAll());
    }

    @Override
    public ProvincesDTO findById(Integer provincesId) {
        return createFromE(provincesRepository.getById(provincesId));
    }

    @Override
    public ProvincesDTO update(Integer provincesId, ProvincesDTO provincesDTO) {
        return null;
    }

    @Override
    public ProvincesDTO save(ProvincesDTO provincesDTO) {
        return null;
    }

    @Override
    public ProvincesDTO delete(Integer provincesId) {
        return null;
    }

    @Override
    public Provinces createFromD(ProvincesDTO provincesDTO) {
        Provinces provinces = modelMapper.map(provincesDTO, Provinces.class);
        return provinces;
    }

    @Override
    public ProvincesDTO createFromE(Provinces provinces) {
        ProvincesDTO provincesDTO = modelMapper.map(provinces, ProvincesDTO.class);
        return provincesDTO;
    }

    @Override
    public Provinces updateEntity(Provinces provinces, ProvincesDTO provincesDTO) {
        return null;
    }
}
