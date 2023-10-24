package com.n19dccn112.service;

import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.model.dto.UserDetailDTO;
import com.n19dccn112.model.entity.UserDetail;
import com.n19dccn112.repository.*;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class UserDetailService implements IBaseService<UserDetailDTO, Long>, IModelMapper<UserDetailDTO, UserDetail> {
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProvincesRepository provincesRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final ModelMapper modelMapper;

    public UserDetailService(UserDetailRepository userDetailRepository, UserRepository userRepository, UserService userService, ProvincesRepository provincesRepository, DistrictRepository districtRepository, WardRepository wardRepository, ModelMapper modelMapper) {
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.provincesRepository = provincesRepository;
        this.districtRepository = districtRepository;
        this.wardRepository = wardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDetailDTO> findAll() {
        return createFromEntities(userDetailRepository.findAll());
    }

    public List<UserDetailDTO> findAllByUserId(Long userId){
        return createFromEntities(userDetailRepository.findAllByUser_UserId(userId));
    }

    @Override
    public UserDetailDTO findById(Long unitDetailId) {
        return createFromE(userDetailRepository.findById(unitDetailId).get());
    }
    public UserDetailDTO findByUserDetailIdNewSave(String nameUserDetail){
        return createFromE(userDetailRepository.findById(userDetailRepository.userDetailIdNewSave(nameUserDetail)).get());
    }

    @Override
    public UserDetailDTO update(Long unitDetailId, UserDetailDTO userDetailDTO) {
        UserDetail userDetail = userDetailRepository.findById(unitDetailId).get();
        userDetailRepository.save(updateEntity(userDetail, userDetailDTO));
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO save(UserDetailDTO userDetailDTO) {
        Boolean hasDefault = true;
        if (userDetailRepository.findAllByUser_UserId(userDetailDTO.getUserId()).size() == 0){
            hasDefault = false;
        }
        userDetailRepository.save(createFromD(userDetailDTO));
        if (!hasDefault){
            UserDTO userDTO = userService.findById(userDetailDTO.getUserId());
            userDTO.setUserDetailIdDefault(userDetailRepository.userDetailIdNewSave(userDetailDTO.getName()));
            userService.update(userDetailDTO.getUserId(), userDTO);
        }
        return userDetailDTO;
    }

    @Override
    public UserDetailDTO delete(Long unitDetailId) {
        UserDetail userDetail = userDetailRepository.findById(unitDetailId).get();
        try {
            userDetailRepository.delete(userDetail);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(UserDetail.class, unitDetailId);
        }
        return createFromE(userDetail);
    }

    @Override
    public UserDetail createFromD(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = modelMapper.map(userDetailDTO, UserDetail.class);
        userDetail.setUser(userRepository.findById(userDetailDTO.getUserId()).get());
        userDetail.setProvinces(provincesRepository.findById(userDetailDTO.getProvinceId()).get());
        userDetail.setDistrict(districtRepository.findById(userDetailDTO.getDistrictId()).get());
        userDetail.setWard(wardRepository.findById(userDetailDTO.getWardId()).get());
        return userDetail;
    }

    @Override
    public UserDetailDTO createFromE(UserDetail userDetail) {
        UserDetailDTO userDetailDTO = modelMapper.map(userDetail, UserDetailDTO.class);
        userDetailDTO.setUserId(userDetail.getUser().getUserId());
        userDetailDTO.setProvinceId(userDetail.getProvinces().getProvincesId());
        userDetailDTO.setDistrictId(userDetail.getDistrict().getDistrictsId());
        userDetailDTO.setWardId(userDetail.getWard().getWardsId());
        userDetailDTO.setProvinceName(userDetail.getProvinces().getProvincesName());
        userDetailDTO.setDistrictName(userDetail.getDistrict().getDistrictsName());
        userDetailDTO.setWardName(userDetail.getWard().getWardsName());
        return userDetailDTO;
    }

    @Override
    public UserDetail updateEntity(UserDetail userDetail, UserDetailDTO userDetailDTO) {
        if (userDetail != null && userDetailDTO != null){
            userDetail.setAddress(userDetailDTO.getAddress());
            userDetail.setName(userDetailDTO.getName());
            userDetail.setUser(userRepository.findById(userDetailDTO.getUserId()).get());
            userDetail.setPhone(userDetail.getPhone());
            userDetail.setProvinces(provincesRepository.findById(userDetailDTO.getProvinceId()).get());
            userDetail.setDistrict(districtRepository.findById(userDetailDTO.getDistrictId()).get());
            userDetail.setWard(wardRepository.findById(userDetailDTO.getWardId()).get());
        }
        return userDetail;
    }
}
