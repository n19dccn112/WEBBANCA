package com.n19dccn112.service;

import com.n19dccn112.model.dto.RoleDTO;
import com.n19dccn112.model.entity.Role;
import com.n19dccn112.repository.RoleRepository;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class RoleService implements IBaseService<RoleDTO, Long>, IModelMapper<RoleDTO, Role> {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleDTO> findAll() {
        return createFromEntities(roleRepository.findAll());
    }

    @Override
    public RoleDTO findById(Long roleId) {
        return createFromE(roleRepository.findById(roleId).get());
    }

    @Override
    public RoleDTO update(Long roleId, RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleId).get();
        roleRepository.save(updateEntity(role, roleDTO));
        return roleDTO;
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        roleRepository.save(createFromD(roleDTO));
        return roleDTO;
    }

    @Override
    public RoleDTO delete(Long roleId) {
        Role role = roleRepository.findById(roleId).get();
        try {
            roleRepository.delete(role);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(Role.class, roleId);
        }
        return createFromE(role);
    }

    @Override
    public Role createFromD(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        return role;
    }

    @Override
    public RoleDTO createFromE(Role role) {
        RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
        return roleDTO;
    }

    @Override
    public Role updateEntity(Role role, RoleDTO roleDTO) {
        if (role != null && roleDTO != null){
            role.setName(roleDTO.getName());
        }
        return role;
    }
}
